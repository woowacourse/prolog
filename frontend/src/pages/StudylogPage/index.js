/** @jsxImportSource @emotion/react */

import { useContext, useEffect, useRef } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import TagManager from 'react-gtm-module';

import Content from './Content';
import { Button, BUTTON_SIZE } from '../../components';
import { ButtonList, EditButtonStyle, DeleteButtonStyle } from './styles';

import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';
import useSnackBar from '../../hooks/useSnackBar';
import useStudylog from '../../hooks/useStudylog';
import useCustomMutation from '../../hooks/useMutation';
import debounce from '../../utils/debounce';

import {
  requestPostScrap,
  requestDeleteScrap,
  requestDeleteLike,
  requestDeleteStudylog,
  requestPostLike,
} from '../../service/requests';

import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  ERROR_MESSAGE,
  PATH,
  SNACKBAR_MESSAGE,
} from '../../constants';
import { SUCCESS_MESSAGE } from '../../constants/message';
import CommentList from '../../components/Comment/CommentList';
import useStudylogComment from '../../hooks/Comment/useStudylogComment';
import useBeforeunload from '../../hooks/useBeforeunload';

const StudylogPage = () => {
  const { id } = useParams();
  const history = useHistory();

  const { user } = useContext(UserContext);
  const { accessToken, isLoggedIn, username, userId } = user;

  const { openSnackBar } = useSnackBar();

  const onSuccessWriteStudylog = (studylog) => {
    TagManager.dataLayer({
      dataLayer: {
        event: 'page_view_studylog',
        mine: username === studylog.author?.username,
        user_id: userId,
        username,
        target: studylog.id,
      },
    });
  };

  const { response: studylog, getData } = useStudylog({}, onSuccessWriteStudylog);

  const getStudylog = () => getData({ id, accessToken });

  const { mutate: deleteStudylog } = useCustomMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;

      return requestDeleteStudylog({ id, accessToken });
    },
    {
      onSuccess: () => {
        openSnackBar(SUCCESS_MESSAGE.DELETE_STUDYLOG);
        history.push(PATH.STUDYLOG);
      },
      onError: (error) => {
        alert(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_DELETE_STUDYLOG);
      },
    }
  );

  const { author = null, liked = false, scrap = false } = studylog;

  const goAuthorProfilePage = (event) => {
    event.stopPropagation();

    if (!author) {
      return;
    }

    history.push(`/${author?.username}`);
  };

  const goEditTargetPost = () => {
    history.push(`${PATH.STUDYLOG}/${id}/edit`);
  };

  const { mutate: postScrap } = useCustomMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostScrap({ username, accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_SCRAP);
      },
    }
  );

  const { mutate: deleteScrap } = useCustomMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) return;

      return requestDeleteScrap({ username, accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.DELETE_SCRAP);
      },
    }
  );

  const { mutate: postLike } = useCustomMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostLike({ accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.SET_LIKE);
      },
      onError: () => openSnackBar(SNACKBAR_MESSAGE.ERROR_SET_LIKE),
    }
  );

  const { mutate: deleteLike } = useCustomMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_LIKE)) return;

      return requestDeleteLike({ accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.UNSET_LIKE);
      },
      onError: () => openSnackBar(SNACKBAR_MESSAGE.ERROR_UNSET_LIKE),
    }
  );

  const toggleLike = () => {
    liked
      ? debounce(() => {
          deleteLike();
        }, 300)
      : debounce(() => {
          postLike();
        }, 300);
  };

  const toggleScrap = () => {
    if (scrap) {
      deleteScrap();
      return;
    }

    postScrap();
  };

  useEffect(() => {
    // accessToken 이 있을 시에 studylogs -> me -> studylogs 순서 제어를 위한 임시 코드
    const timeout = setTimeout(() => getStudylog(), 0);

    return () => {
      clearTimeout(timeout);
    };
  }, [accessToken, id]);

  /* 댓글 로직 */
  const { comments, createComment, editComment, deleteComment } = useStudylogComment(id);

  const editorContentRef = useRef(null);

  const onSubmitComment = (event) => {
    event.preventDefault();

    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    createComment({ content });
    editorContentRef.current?.getInstance().setMarkdown('');
  };

  useBeforeunload(editorContentRef);

  return (
    <div css={MainContentStyle}>
      {username === author?.username && (
        <ButtonList>
          {[
            { title: '수정', cssProps: EditButtonStyle, onClick: goEditTargetPost },
            { title: '삭제', cssProps: DeleteButtonStyle, onClick: deleteStudylog },
          ].map(({ title, cssProps, onClick }) => (
            <Button
              key={title}
              size={BUTTON_SIZE.X_SMALL}
              type="button"
              cssProps={cssProps}
              onClick={onClick}
            >
              {title}
            </Button>
          ))}
        </ButtonList>
      )}
      <Content
        studylog={{ ...studylog.studylogResponse, scrapedCount: studylog.scrapedCount }}
        toggleLike={toggleLike}
        toggleScrap={toggleScrap}
        goAuthorProfilePage={goAuthorProfilePage}
      />
      {comments && (
        <CommentList
          comments={comments}
          editComment={editComment}
          deleteComment={deleteComment}
          onSubmit={onSubmitComment}
          editorContentRef={editorContentRef}
        />
      )}
    </div>
  );
};

export default StudylogPage;
