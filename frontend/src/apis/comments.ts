import axios from 'axios';
import { BASE_URL } from '../configs/environment';
import { CommentType } from '../models/Comments';

export const getComments = async (studylogId: number): Promise<CommentType[]> => {
  const response = await axios.get(`${BASE_URL}/studylogs/${studylogId}/comments`);

  return response.data;
};
