import axios from 'axios';
import { BASE_URL } from '../configs/environment';
import { CommentListResponse } from '../models/Comments';

export const getComments = async (studylogId: number): Promise<CommentListResponse> => {
  const response = await axios.get(`${BASE_URL}/studylogs/${studylogId}/comments`);

  return response.data;
};

export const createCommentRequest = ({
  studylogId,
  body,
}: {
  studylogId: number;
  body: { content: string };
}) => axios.post(`${BASE_URL}/studylogs/${studylogId}/comments`, body);

export const editComment = ({
  studylogId,
  commentId,
  body,
}: {
  studylogId: number;
  commentId: number;
  body: { content: string };
}) => axios.patch(`${BASE_URL}/studylogs/${studylogId}/comments/${commentId}`, body);

export const deleteComment = ({
  studylogId,
  commentId,
}: {
  studylogId: number;
  commentId: number;
}) => axios.delete(`${BASE_URL}/studylogs/${studylogId}/comments/${commentId}`);
