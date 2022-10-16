import { client } from '.';
import { KeywordListResponse, KeywordResponse } from '../models/Keywords';

export const getKeyword = async ({
  sessionId,
  keywordId,
}: {
  sessionId: number;
  keywordId: number;
}) => {
  const response = await client.get<KeywordResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}`
  );

  return response.data;
};

export const getTopKeywordList = async (sessionId: number) => {
  console.log('sessionId', sessionId);
  const response = await client.get<KeywordListResponse>(`/sessions/${sessionId}/keywords`);

  return response.data;
};

export const getChildKeywordList = async ({
  sessionId,
  keywordId,
}: {
  sessionId: number;
  keywordId: number;
}) => {
  const response = await client.get<KeywordListResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}/children`
  );

  return response.data;
};

export const getQuizListByKeyword = async ({
  sessionId,
  keywordId,
}: {
  sessionId: number;
  keywordId: number;
}) => {
  const response = await client.get<KeywordListResponse>(
    `/sessions/${sessionId}/keywords/${keywordId}/quizs`
  );

  return response.data;
};
