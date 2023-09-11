import { client } from '.';
import {
  KeywordRequest,
  KeywordResponse,
  QuizListByKeywordRequest,
  QuizListResponse,
  RoadmapRequest,
  RoadmapResponse,
} from '../models/Keywords';
import { createAxiosInstance } from '../utils/axiosInstance';

const instanceWithoutToken = createAxiosInstance();

export const getRoadmap = async ({ curriculumId }: RoadmapRequest) => {
  const response = await client.get<RoadmapResponse>(`/roadmaps?curriculumId=${curriculumId}`);

  return response.data;
};

export const getKeyword = async ({ keywordId }: KeywordRequest) => {
  const response = await instanceWithoutToken.get<KeywordResponse>(`/keywords/${keywordId}`);

  return response.data;
};

export const getQuizListByKeyword = async ({ keywordId }: QuizListByKeywordRequest) => {
  const response = await instanceWithoutToken.get<QuizListResponse>(
    `/quizzes?keywordId=${keywordId}`
  );

  return response.data;
};
