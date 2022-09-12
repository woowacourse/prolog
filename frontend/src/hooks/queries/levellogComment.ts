import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  createLevellogComment,
  deleteLevellogComment,
  editLevellogComment,
  getLevellogComments,
} from '../../apis/levellogComment';
import { CommentRequest } from '../../models/Comment';

const QUERY_KEY = {
  levellogComments: 'levellogComments',
};

export const useFetchLevellogComments = (levellogId: number) =>
  useQuery([QUERY_KEY.levellogComments, levellogId], () => getLevellogComments(levellogId));

export const useCreateLevellogComment = (levellogId: number) => {
  const queryClient = useQueryClient();

  return useMutation((body: CommentRequest) => createLevellogComment({ levellogId, body }), {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.levellogComments, levellogId]);
    },
  });
};

export const useEditLevellogCommentMutation = (levellogId: number) => {
  const queryClient = useQueryClient();

  return useMutation(
    ({ commentId, body }: { commentId: number; body: CommentRequest }) =>
      editLevellogComment({ levellogId, commentId, body }),
    {
      onSuccess() {
        queryClient.invalidateQueries([QUERY_KEY.levellogComments, levellogId]);
      },
    }
  );
};

export const useDeleteLevellogCommentMutation = (levellogId: number) => {
  const queryClient = useQueryClient();

  return useMutation((commentId: number) => deleteLevellogComment({ levellogId, commentId }), {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.levellogComments, levellogId]);
    },
  });
};
