import { EssayAnswerRequest } from '../models/EssayAnswers';
import { AxiosPromise, AxiosResponse } from 'axios';
import { Quiz } from '../models/Keywords';
import { createAxiosInstance } from '../utils/axiosInstance';

const instanceWithoutToken = createAxiosInstance();

export const createNewEssayAnswerRequest = (body: EssayAnswerRequest) =>
  instanceWithoutToken.post(`/essay-answers`, body);

export const requestGetEssayAnswer = async (essayAnswerId) => {
  const { data } = await instanceWithoutToken.get(`/essay-answers/${essayAnswerId}`);

  return data;
};

export const requestDeleteEssayAnswer = async (essayAnswerId) => {
  await instanceWithoutToken.delete(`/essay-answers/${essayAnswerId}`);
};

export const requestGetEssayAnswerList = async (quizId) => {
  const { data } = await instanceWithoutToken.get(`/quizzes/${quizId}/essay-answers`);

  return data;
};

export const requestGetQuizAsync = async (quizId) => {
  const { data } = await instanceWithoutToken.get(`/quizzes/${quizId}`);

  return data;
};

export const requestGetQuiz = (quizId: Number): AxiosPromise<AxiosResponse<Quiz>> =>
  instanceWithoutToken.get<AxiosResponse<Quiz>>(`/quizzes/${quizId}`);
