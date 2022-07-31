import { client } from '.';
import { CommentListResponse, CommentRequest } from '../models/Comments';

export const getComments = async (studylogId: number): Promise<CommentListResponse> => {
  const response = await client.get(`/studylogs/${studylogId}/comments`);

  return response.data;
};

export const createCommentRequest = ({
  studylogId,
  body,
}: {
  studylogId: number;
  body: CommentRequest;
}) => client.post(`/studylogs/${studylogId}/comments`, body);

export const editComment = ({
  studylogId,
  commentId,
  body,
}: {
  studylogId: number;
  commentId: number;
  body: CommentRequest;
}) => client.patch(`/studylogs/${studylogId}/comments/${commentId}`, body);

export const deleteComment = ({
  studylogId,
  commentId,
}: {
  studylogId: number;
  commentId: number;
}) => client.delete(`/studylogs/${studylogId}/comments/${commentId}`);
