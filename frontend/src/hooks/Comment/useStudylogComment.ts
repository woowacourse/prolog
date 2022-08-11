import { CommentRequest } from '../../models/Comment';
import {
  useCreateComment,
  useDeleteCommentMutation,
  useEditCommentMutation,
  useFetchComments,
} from '../queries/comment';

const useStudylogComment = (studylogId: number) => {
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

  return {
    comments,
    createComment,
    editComment,
    deleteComment,
  };
};

export default useStudylogComment;
