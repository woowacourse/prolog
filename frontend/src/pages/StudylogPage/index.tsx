/** @jsxImportSource @emotion/react */

import { MouseEvent, useContext, useEffect, useRef } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import TagManager from 'react-gtm-module';

import Content from './Content';
import { EditorForm, SubmitButton } from './styles';

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
import { Card, SectionName } from '../../components/StudylogEditor/styles';
import { css } from '@emotion/react';
import QuestionAnswers from '../../components/StudylogEditor/QuestionAnswers';

const StudylogPage = () => {
  const { id } = useParams<{ id: string }>();
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

  const goAuthorProfilePage = (event: MouseEvent<HTMLDivElement>) => {
    event.stopPropagation();

    if (!author) {
      return;
    }

    history.push(`/${author?.username}`);
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
  const { comments, createComment, editComment, deleteComment } = useStudylogComment(Number(id));

  const editorContentRef = useRef<any>(null);

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
      <Content
        studylog={studylog}
        answers={studylog.answers}
        toggleLike={toggleLike}
        toggleScrap={toggleScrap}
        goAuthorProfilePage={goAuthorProfilePage}
      />
      {studylog.answers && studylog.answers.length > 0 && (
        <Card
          css={css`
            padding: 2.5rem 4rem 5rem 4rem;
            margin-top: 3rem;
          `}
        >
          <SectionName>Question</SectionName>
          <QuestionAnswers editable={false} questionAnswers={studylog.answers} />
        </Card>
      )}
      <Card
        css={css`
          padding: 2.5rem 4rem 5rem 4rem;
          margin-top: 3rem;
        `}
      >
        <SectionName>Comment</SectionName>
        {isLoggedIn && (
          <EditorForm onSubmit={onSubmitComment}>
            <Editor height="25rem" hasTitle={false} editorContentRef={editorContentRef} />
            <SubmitButton>작성 완료</SubmitButton>
          </EditorForm>
        )}
        {comments && (
          <CommentList
            comments={comments}
            editComment={editComment}
            deleteComment={deleteComment}
          />
        )}
      </Card>
    </div>
  );
};

export default StudylogPage;
