import { AxiosPromise, AxiosResponse } from 'axios';
import { client } from '.';
import {
  EssayAnswerListRequest,
  EssayAnswerRequest,
  EssayEditRequest,
} from '../models/EssayAnswers';
import { Quiz } from '../models/Keywords';

export const createNewEssayAnswerRequest = (body: EssayAnswerRequest) =>
  client.post(`/essay-answers`, body);

export const requestGetEssayAnswers = async (params: EssayAnswerListRequest) => {
  const { quizIds, memberIds, ...otherParams } = params;

  const axiosParams: Omit<EssayAnswerListRequest, 'quizIds' | 'memberIds'> & {
    quizIds?: string;
    memberIds?: string;
  } = { ...otherParams };

  if (quizIds) axiosParams.quizIds = quizIds.join(',');
  if (memberIds) axiosParams.memberIds = memberIds.join(',');

  const { data } = await client.get('/essay-answers', {
    params: axiosParams,
  });

  return data;
};

export const requestGetEssayAnswer = async (essayAnswerId: number) => {
  const { data } = await client.get(`/essay-answers/${essayAnswerId}`);
  return data;
};

export const requestEditEssayAnswer = async (essayAnswerId: number, body: EssayEditRequest) => {
  await client.patch(`/essay-answers/${essayAnswerId}`, body);
};

export const requestDeleteEssayAnswer = async (essayAnswerId: number) => {
  await client.delete(`/essay-answers/${essayAnswerId}`);
};

export const requestGetQuizAnswers = async (quizId: number) => {
  const { data } = await client.get(`/quizzes/${quizId}/essay-answers`);

  return data;
};

export const requestGetQuizAsync = async (quizId: number) => {
  const { data } = await client.get(`/quizzes/${quizId}`);

  return data;
};

export const requestGetQuiz = (quizId: Number): AxiosPromise<AxiosResponse<Quiz>> =>
  client.get<AxiosResponse<Quiz>>(`/quizzes/${quizId}`);

export const requestGetQuizzes = async (curriculumId: number) => {
  const { data } = await client.get<{ id: number; question: string }[]>(
    `/curriculums/${curriculumId}/quizzes`
  );
  return data;
};
