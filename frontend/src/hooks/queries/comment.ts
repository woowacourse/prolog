import { useMutation, useQuery, useQueryClient } from 'react-query';
import { createCommentRequest, getComments } from '../../apis/comments';

export const useFetchComments = (studylogId: number) =>
  useQuery(['comments', studylogId], () => getComments(studylogId));

export const useCreateComment = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation(createCommentRequest, {
    onSuccess() {
      queryClient.invalidateQueries(['comments', studylogId]);
    },
  });
};
