import { CommentRequest, CommentType } from '../../models/Comment';
import Comment from './Comment';
import * as Styled from './CommentList.style';
import { FormEventHandler, MutableRefObject } from 'react';
import { Editor as ToastEditor } from '@toast-ui/react-editor';

interface CommentListProps {
  comments: CommentType[];
  editComment: (commentId: number, body: CommentRequest) => void;
  deleteComment: (commentId: number) => void;
}

const CommentList = ({
  comments,
  editComment,
  deleteComment,
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
    </Styled.CommentsContainer>
  );
};

export default CommentList;
