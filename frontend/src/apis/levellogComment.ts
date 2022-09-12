import { client } from '.';
import { CommentListResponse, CommentRequest } from '../models/Comment';

export const getLevellogComments = async (levellogId: number): Promise<CommentListResponse> => {
  const response = await client.get(`/levellogs/${levellogId}/comments`);

  return response.data;
};

export const createLevellogComment = ({
  levellogId,
  body,
}: {
  levellogId: number;
  body: CommentRequest;
}) => client.post(`/levellogs/${levellogId}/comments`, body);

export const editLevellogComment = ({
  levellogId,
  commentId,
  body,
}: {
  levellogId: number;
  commentId: number;
  body: CommentRequest;
}) => client.put(`/levellogs/${levellogId}/comments/${commentId}`, body);

export const deleteLevellogComment = ({
  levellogId,
  commentId,
}: {
  levellogId: number;
  commentId: number;
}) => client.delete(`/levellogs/${levellogId}/comments/${commentId}`);
