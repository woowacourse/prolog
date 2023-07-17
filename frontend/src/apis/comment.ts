import { createAxiosInstance } from '../utils/axiosInstance';
import { CommentListResponse, CommentRequest } from '../models/Comment';

const customAxios = createAxiosInstance();

export const getComments = async (studylogId: number): Promise<CommentListResponse> => {
  const response = await customAxios.get(`/studylogs/${studylogId}/comments`);

  return response.data;
};

export const createCommentRequest = ({
  studylogId,
  body,
}: {
  studylogId: number;
  body: CommentRequest;
}) => customAxios.post(`/studylogs/${studylogId}/comments`, body);

export const editComment = ({
  studylogId,
  commentId,
  body,
}: {
  studylogId: number;
  commentId: number;
  body: CommentRequest;
}) => customAxios.put(`/studylogs/${studylogId}/comments/${commentId}`, body);

export const deleteComment = ({
  studylogId,
  commentId,
}: {
  studylogId: number;
  commentId: number;
}) => customAxios.delete(`/studylogs/${studylogId}/comments/${commentId}`);
