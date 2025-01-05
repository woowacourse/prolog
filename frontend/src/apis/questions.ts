import { AxiosResponse } from 'axios';
import { createAxiosInstance } from '../utils/axiosInstance';

const instanceWithoutToken = createAxiosInstance();

/** 질문 조회 **/
export const fetchQuestionsByMissionId = (missionId: number): Promise<AxiosResponse<any>> =>
  instanceWithoutToken.get(`/questions?missionId=${missionId}`);
