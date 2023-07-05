import { client } from '.';
import { CurriculumListResponse } from '../models/Keywords';

export const getCurriculums = async () => {
  const response = await client.get<CurriculumListResponse>(`/curriculums`);

  return response.data;
};
