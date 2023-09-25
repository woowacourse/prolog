import { client } from '.';
import {
  KeywordRequest,
  KeywordResponse,
  QuizListByKeywordRequest,
  QuizListResponse,
  RoadmapRequest,
  RoadmapResponse,
} from '../models/Keywords';

export const getRoadmap = async ({ curriculumId }: RoadmapRequest) => {
  const response = await client.get<RoadmapResponse>(`/roadmaps?curriculumId=${curriculumId}`);

  return response.data;
};

export const getKeyword = async ({ keywordId }: KeywordRequest) => {
  const response = await client.get<KeywordResponse>(`/keywords/${keywordId}`);

  return response.data;
};

export const getQuizListByKeyword = async ({ keywordId }: QuizListByKeywordRequest) => {
  const response = await client.get<QuizListResponse>(`/quizzes?keywordId=${keywordId}`);

  return response.data;
};
