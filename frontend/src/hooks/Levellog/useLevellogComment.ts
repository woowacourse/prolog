import { CommentRequest } from '../../models/Comment';
import {
  useCreateLevellogComment,
  useDeleteLevellogCommentMutation,
  useEditLevellogCommentMutation,
  useFetchLevellogComments,
} from '../queries/levellogComment';

const useLevellogComment = (levellogId: number) => {
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

  return {
    levellogComments,
    createLevellogComment,
    editLevellogComment,
    deleteLevellogComment,
  };
};

export default useLevellogComment;
