import { createAxiosInstance } from '../utils/axiosInstance';
import { CommentListResponse, CommentRequest } from '../models/Comment';

const instanceWithoutToken = createAxiosInstance();
const instanceWithToken = (accessToken) => createAxiosInstance({ accessToken });

export const getComments = async (studylogId: number): Promise<CommentListResponse> => {
  const response = await instanceWithoutToken.get(`/studylogs/${studylogId}/comments`);

  return response.data;
};

export const createCommentRequest = ({
  studylogId,
  body,
  accessToken,
}: {
  studylogId: number;
  body: CommentRequest;
  accessToken: string;
}) => instanceWithToken(accessToken).post(`/studylogs/${studylogId}/comments`, body);

export const editComment = ({
  studylogId,
  commentId,
  body,
  accessToken,
}: {
  studylogId: number;
  commentId: number;
  body: CommentRequest;
  accessToken: string;
}) => instanceWithToken(accessToken).put(`/studylogs/${studylogId}/comments/${commentId}`, body);

export const deleteComment = ({
  studylogId,
  commentId,
  accessToken,
}: {
  studylogId: number;
  commentId: number;
  accessToken: string;
}) => instanceWithToken(accessToken).delete(`/studylogs/${studylogId}/comments/${commentId}`);
