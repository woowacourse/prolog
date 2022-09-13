import { useRef } from 'react';
import { Editor as ToastEditor } from '@toast-ui/react-editor';

import { ALERT_MESSAGE } from '../../constants';
import { CommentRequest } from '../../models/Comment';
import {
  useCreateComment,
  useDeleteCommentMutation,
  useEditCommentMutation,
  useFetchComments,
} from '../queries/comment';

const useStudylogComment = (studylogId: number) => {
  const editorContentRef = useRef<ToastEditor>(null);

  const { data } = useFetchComments(studylogId);
  const comments = data?.data;

  const createCommentMutation = useCreateComment(studylogId);
  const editCommentMutation = useEditCommentMutation(studylogId);
  const deleteCommentMutation = useDeleteCommentMutation(studylogId);

  const createComment = (body: CommentRequest) => {
    createCommentMutation.mutate(body);
  };

  const editComment = (commentId: number, body: CommentRequest) => {
    editCommentMutation.mutate({ commentId, body });
  };

  const deleteComment = (commentId: number) => {
    deleteCommentMutation.mutate(commentId);
  };

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

  return {
    comments,
    editorContentRef,
    createComment,
    editComment,
    deleteComment,
    onSubmitComment,
  };
};

export default useStudylogComment;
