import { createAxiosInstance } from '../utils/axiosInstance';
import { CurriculumListResponse } from '../models/Keywords';

const instanceWithoutToken = createAxiosInstance();

export const getCurriculums = async () => {
  const response = await instanceWithoutToken.get<CurriculumListResponse>(`/curriculums`);

  return response.data;
};
