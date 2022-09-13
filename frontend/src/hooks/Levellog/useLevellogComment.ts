import { useRef } from 'react';
import { Editor as ToastEditor } from '@toast-ui/react-editor';

import { CommentRequest } from '../../models/Comment';
import {
  useCreateLevellogComment,
  useDeleteLevellogCommentMutation,
  useEditLevellogCommentMutation,
  useFetchLevellogComments,
} from '../queries/levellogComment';
import { ALERT_MESSAGE } from '../../constants';

const useLevellogComment = (levellogId: number) => {
  const editorContentRef = useRef<ToastEditor>(null);
  const { data } = useFetchLevellogComments(levellogId);
  const levellogComments = data?.data;

  const createLevellogCommentMutation = useCreateLevellogComment(levellogId);
  const editLevellogCommentMutation = useEditLevellogCommentMutation(levellogId);
  const deleteLevellogCommentMutation = useDeleteLevellogCommentMutation(levellogId);

  const createLevellogComment = (body: CommentRequest) => {
    createLevellogCommentMutation.mutate(body);
  };

  const editLevellogComment = (commentId: number, body: CommentRequest) => {
    editLevellogCommentMutation.mutate({ commentId, body });
  };

  const deleteLevellogComment = (commentId: number) => {
    deleteLevellogCommentMutation.mutate(commentId);
  };

  const onSubmitLevellogComment = (event) => {
    event.preventDefault();

    const content = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (content.length === 0) {
      alert(ALERT_MESSAGE.NO_CONTENT);
      return;
    }

    createLevellogComment({ content });
    editorContentRef.current?.getInstance().setMarkdown('');
  };

  return {
    levellogComments,
    editorContentRef,
    createLevellogComment,
    editLevellogComment,
    deleteLevellogComment,
    onSubmitLevellogComment,
  };
};

export default useLevellogComment;
