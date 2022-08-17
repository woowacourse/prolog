/** @jsxImportSource @emotion/react */

import { useContext, useEffect, useRef } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import TagManager from 'react-gtm-module';

import Content from './Content';
import { Button, BUTTON_SIZE } from '../../components';
import { ButtonList, EditButtonStyle, DeleteButtonStyle, EditorForm, SubmitButton } from './styles';

import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';
import useStudylog from '../../hooks/useStudylog';
import debounce from '../../utils/debounce';

import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';

import CommentList from '../../components/Comment/CommentList';
import useStudylogComment from '../../hooks/Comment/useStudylogComment';
import useBeforeunload from '../../hooks/useBeforeunload';
import Editor from '../../components/Editor/Editor';
import {
  useDeleteLikeMutation,
  useDeleteScrapMutation,
  useDeleteStudylogMutation,
  usePostLikeMutation,
  usePostScrapMutation,
} from '../../hooks/queries/studylog';

const StudylogPage = () => {
  const { id } = useParams();
  const history = useHistory();

  const { user } = useContext(UserContext);
  const { accessToken, isLoggedIn, username, userId } = user;

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

  const getStudylog = () => getData({ id, accessToken });

  const { response: studylog, getData } = useStudylog({}, onSuccessWriteStudylog);
  const { mutate: deleteStudylog } = useDeleteStudylogMutation();
  const { mutate: postScrap } = usePostScrapMutation({ getStudylog });
  const { mutate: deleteScrap } = useDeleteScrapMutation({ getStudylog });
  const { mutate: postLike } = usePostLikeMutation({ getStudylog });
  const { mutate: deleteLike } = useDeleteLikeMutation({ getStudylog });

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

  const toggleLike = () => {
    liked
      ? debounce(() => {
          if (!window.confirm(CONFIRM_MESSAGE.DELETE_LIKE)) return;
          deleteLike({ accessToken, id });
        }, 300)
      : debounce(() => {
          if (!isLoggedIn) {
            alert(ALERT_MESSAGE.NEED_TO_LOGIN);
            return;
          }
          postLike({ accessToken, id });
        }, 300);
  };

  const toggleScrap = () => {
    if (scrap) {
      deleteScrap({ username, accessToken, id });
      return;
    }

    postScrap({ username, accessToken, id });
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
            {
              title: '삭제',
              cssProps: DeleteButtonStyle,
              onClick: () => {
                if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;
                deleteStudylog({ id, accessToken });
              },
            },
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
      {comments && (
        <CommentList comments={comments} editComment={editComment} deleteComment={deleteComment} />
      )}
      {isLoggedIn && (
        <EditorForm onSubmit={onSubmitComment}>
          <Editor height="25rem" hasTitle={false} editorContentRef={editorContentRef} />
          <SubmitButton>작성 완료</SubmitButton>
        </EditorForm>
      )}
    </div>
  );
};

export default StudylogPage;
