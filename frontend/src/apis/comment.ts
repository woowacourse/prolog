import { createAxiosInstance } from '../utils/axiosInstance';
import { CommentListResponse, CommentRequest } from '../models/Comment';

const instanceWithoutToken = createAxiosInstance();

export const getComments = async (studylogId: number): Promise<CommentListResponse> => {
  const response = await instanceWithoutToken.get(`/studylogs/${studylogId}/comments`);

  return response.data;
};

export const createCommentRequest = ({
  studylogId,
  body,
}: {
  studylogId: number;
  body: CommentRequest;
}) => instanceWithoutToken.post(`/studylogs/${studylogId}/comments`, body);

export const editComment = ({
  studylogId,
  commentId,
  body,
}: {
  studylogId: number;
  commentId: number;
  body: CommentRequest;
}) => instanceWithoutToken.put(`/studylogs/${studylogId}/comments/${commentId}`, body);

export const deleteComment = ({
  studylogId,
  commentId,
}: {
  studylogId: number;
  commentId: number;
}) => instanceWithoutToken.delete(`/studylogs/${studylogId}/comments/${commentId}`);
