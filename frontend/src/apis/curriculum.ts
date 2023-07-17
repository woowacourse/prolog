import { createAxiosInstance } from '../utils/axiosInstance';
import { CurriculumListResponse } from '../models/Keywords';

const customAxios = createAxiosInstance();

export const getCurriculums = async () => {
  const response = await customAxios.get<CurriculumListResponse>(`/curriculums`);

  return response.data;
};
