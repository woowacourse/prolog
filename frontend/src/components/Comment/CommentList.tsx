import { CommentRequest, CommentType } from '../../models/Comments';
import Editor from '../Editor/Editor';
import Comment from './Comment';
import * as Styled from './CommentList.style';
import { FormEventHandler, MutableRefObject } from 'react';
import { Editor as ToastEditor } from '@toast-ui/react-editor';

interface CommentListProps {
  comments: CommentType[];
  editComment: (commentId: number, body: CommentRequest) => void;
  deleteComment: (commentId: number) => void;
  onSubmit: FormEventHandler<HTMLFormElement>;
  editorContentRef: MutableRefObject<ToastEditor>;
}

const CommentList = ({
  comments,
  editComment,
  deleteComment,
  onSubmit,
  editorContentRef,
}: CommentListProps) => {
  return (
    <Styled.CommentsContainer>
      {comments?.map((comment) => (
        <Comment
          key={comment.id}
          editComment={editComment}
          deleteComment={deleteComment}
          {...comment}
        />
      ))}
      <Styled.EditorForm onSubmit={onSubmit}>
        <Editor height="25rem" hasTitle={false} editorContentRef={editorContentRef} />
        <Styled.SubmitButton>작성 완료</Styled.SubmitButton>
      </Styled.EditorForm>
    </Styled.CommentsContainer>
  );
};

export default CommentList;
