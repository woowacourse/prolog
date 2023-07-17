import { createAxiosInstance } from '../utils/axiosInstance';
import {
  KeywordResponse,
  QuizListResponse,
  SubKeywordListResponse,
  TopKeywordListResponse,
} from '../models/Keywords';
import {
  KeywordRequest,
  ChildKeywordListRequest,
  QuizListByKeywordRequest,
} from '../models/Keywords';

const customAxios = createAxiosInstance();

export const getKeyword = async ({ sessionId, keywordId }: KeywordRequest) => {
  const response = await customAxios.get<KeywordResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}`
  );

  return response.data;
};

export const getTopKeywordList = async (sessionId: number) => {
  const response = await customAxios.get<TopKeywordListResponse>(`/sessions/${sessionId}/keywords`);

  return response.data;
};

export const getChildKeywordList = async ({ sessionId, keywordId }: ChildKeywordListRequest) => {
  const response = await customAxios.get<SubKeywordListResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}/children`
  );

  return response.data;
};

export const getQuizListByKeyword = async ({ sessionId, keywordId }: QuizListByKeywordRequest) => {
  const response = await customAxios.get<QuizListResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}/quizs`
  );

  return response.data;
};
