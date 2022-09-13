/** @jsxImportSource @emotion/react */

import Content from './Content';
import { Button, BUTTON_SIZE } from '../../components';
import { ButtonList, EditButtonStyle, DeleteButtonStyle } from './styles';

import { MainContentStyle } from '../../PageRouter';

import useLevellog from '../../hooks/Levellog/useLevellog';
import QnAList from './QnAList';
import { CONFIRM_MESSAGE } from '../../constants';
import { useParams } from 'react-router-dom';
import useLevellogComment from '../../hooks/Levellog/useLevellogComment';
import Editor from '../../components/Editor/Editor';
import { EditorForm, SubmitButton } from '../StudylogPage/styles';
import CommentList from '../../components/Comment/CommentList';
import { useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';

const LevellogPage = () => {
  const { id } = useParams<{ id: string }>();
  const { user } = useContext(UserContext);
  const { isLoggedIn } = user;

  const {
    levellog,
    deleteLevellog,
    isCurrentUserAuthor,
    isLoading,
    goEditTargetPost,
  } = useLevellog();

  const {
    levellogComments,
    editorContentRef,
    editLevellogComment,
    deleteLevellogComment,
    onSubmitLevellogComment,
  } = useLevellogComment(Number(id));

  return (
    <div css={MainContentStyle}>
      {isCurrentUserAuthor && (
        <ButtonList>
          {[
            { title: '수정', cssProps: EditButtonStyle, onClick: goEditTargetPost },
            {
              title: '삭제',
              cssProps: DeleteButtonStyle,
              onClick: () => {
                if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;
                deleteLevellog();
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
      {isLoading ? (
        <div>로딩중</div>
      ) : (
        <>
          <Content levellog={levellog} />
          <QnAList QnAList={levellog?.levelLogs} />
        </>
      )}
      {levellogComments && (
        <CommentList
          comments={levellogComments}
          editComment={editLevellogComment}
          deleteComment={deleteLevellogComment}
        />
      )}
      {isLoggedIn && (
        <EditorForm onSubmit={onSubmitLevellogComment}>
          <Editor height="25rem" hasTitle={false} editorContentRef={editorContentRef} />
          <SubmitButton>작성 완료</SubmitButton>
        </EditorForm>
      )}
    </div>
  );
};

export default LevellogPage;
