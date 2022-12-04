import { client } from '.';
import { KeywordListResponse, KeywordResponse, QuizListResponse } from '../models/Keywords';
import {
  KeywordRequest,
  ChildKeywordListRequest,
  QuizListByKeywordRequest,
} from '../models/Keywords';

export const getKeyword = async ({ sessionId, keywordId }: KeywordRequest) => {
  const response = await client.get<KeywordResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}`
  );

  return response.data;
};

export const getTopKeywordList = async (sessionId: number) => {
  const response = await client.get<KeywordListResponse>(`/sessions/${sessionId}/keywords`);

  return response.data;
};

export const getChildKeywordList = async ({ sessionId, keywordId }: ChildKeywordListRequest) => {
  const response = await client.get<KeywordListResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}/children`
  );

  return response.data;
};

export const getQuizListByKeyword = async ({ sessionId, keywordId }: QuizListByKeywordRequest) => {
  const response = await client.get<QuizListResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}/quizs`
  );

  return response.data;
};
