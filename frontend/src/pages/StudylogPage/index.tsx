/** @jsxImportSource @emotion/react */

import { MouseEvent, useContext, useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import TagManager from 'react-gtm-module';

import Content from './Content';
import { Button, BUTTON_SIZE } from '../../components';
import { ButtonList, EditButtonStyle, DeleteButtonStyle } from './styles';

import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';
import useSnackBar from '../../hooks/useSnackBar';
import useStudylog from '../../hooks/useStudylog';
import useMutation from '../../hooks/useMutation';
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

const StudylogPage = () => {
  const { id } = useParams<{ id: string }>();
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

  const { mutate: deleteStudylog } = useMutation(
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
      onFinish: () => {},
    }
  );

  const { author = null, liked = false, scrap = false } = studylog;

  const goAuthorProfilePage = (event: MouseEvent<HTMLDivElement>) => {
    event.stopPropagation();

    if (!author) {
      return;
    }

    history.push(`/${author?.username}`);
  };

  const goEditTargetPost = () => {
    history.push(`${PATH.STUDYLOG}/${id}/edit`);
  };

  const { mutate: postScrap } = useMutation(
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
      onError: () => {},
      onFinish: () => {},
    }
  );

  const { mutate: deleteScrap } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) return;

      return requestDeleteScrap({ username, accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.DELETE_SCRAP);
      },
      onError: () => {},
      onFinish: () => {},
    }
  );

  const { mutate: postLike } = useMutation(
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
      onFinish: () => {},
    }
  );

  const { mutate: deleteLike } = useMutation(
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
      onFinish: () => {},
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
        studylog={studylog}
        toggleLike={toggleLike}
        toggleScrap={toggleScrap}
        goAuthorProfilePage={goAuthorProfilePage}
      />
    </div>
  );
};

export default StudylogPage;
