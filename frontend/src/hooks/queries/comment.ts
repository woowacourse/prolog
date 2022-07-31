import { useMutation, useQuery, useQueryClient } from 'react-query';
import { createCommentRequest, deleteComment, editComment, getComments } from '../../apis/comment';
import { CommentRequest } from '../../models/Comment';

const QUERY_KEY = {
  comments: 'comments',
};

export const useFetchComments = (studylogId: number) =>
  useQuery([QUERY_KEY.comments, studylogId], () => getComments(studylogId));

export const useCreateComment = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation((body: CommentRequest) => createCommentRequest({ studylogId, body }), {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.comments, studylogId]);
    },
  });
};

export const useEditCommentMutation = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation(
    ({ commentId, body }: { commentId: number; body: CommentRequest }) =>
      editComment({ studylogId, commentId, body }),
    {
      onSuccess() {
        queryClient.invalidateQueries([QUERY_KEY.comments, studylogId]);
      },
    }
  );
};

export const useDeleteCommentMutation = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation((commentId: number) => deleteComment({ studylogId, commentId }), {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.comments, studylogId]);
    },
  });
};
