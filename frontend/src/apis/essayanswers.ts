import {client} from '.';
import {EssayAnswerRequest, EssayEditRequest} from "../models/EssayAnswers";
import {AxiosPromise, AxiosResponse} from "axios";
import {httpRequester} from "./studylogs";
import {Quiz} from "../models/Keywords";

export const createNewEssayAnswerRequest = (body: EssayAnswerRequest) => client.post(`/essay-answers`, body);

export const requestGetEssayAnswer = async (essayAnswerId) => {
  const { data } = await client.get(`/essay-answers/${essayAnswerId}`);

  return data;
};

export const requestEditEssayAnswer = async (essayAnswerId: number, body: EssayEditRequest) => {
  await client.patch(`/essay-answers/${essayAnswerId}`, body);
}

export const requestDeleteEssayAnswer = async (essayAnswerId) => {
  await client.delete(`/essay-answers/${essayAnswerId}`);
};

export const requestGetEssayAnswerList = async (quizId) => {
  const { data } = await client.get(`/quizzes/${quizId}/essay-answers`);

  return data;
};

export const requestGetQuizAsync = async (quizId) => {
  const { data } = await client.get(`/quizzes/${quizId}`);

  return data;
};

export const requestGetQuiz = (quizId: Number): AxiosPromise<AxiosResponse<Quiz>> => httpRequester.get<AxiosResponse<Quiz>>(`/quizzes/${quizId}`);
