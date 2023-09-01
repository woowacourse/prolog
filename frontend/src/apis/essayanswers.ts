import { EssayAnswerRequest, EssayEditRequest } from "../models/EssayAnswers";
import { AxiosPromise, AxiosResponse } from "axios";
import { Quiz } from "../models/Keywords";
import { createAxiosInstance } from "../utils/axiosInstance";

import LOCAL_STORAGE_KEY from "../constants/localStorage";

const anyoneInstance = createAxiosInstance();
const loggedInUserInstance = createAxiosInstance({
  accessToken: localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN) ?? '',
});

export const createNewEssayAnswerRequest = (body: EssayAnswerRequest) => (
  loggedInUserInstance.post(`/essay-answers`, body)
);

export const requestGetEssayAnswer = async (essayAnswerId) => {
  const { data } = await anyoneInstance.get(`/essay-answers/${essayAnswerId}`);
  return data
};

export const requestEditEssayAnswer = async (essayAnswerId: number, body: EssayEditRequest) => {
  await loggedInUserInstance.patch(`/essay-answers/${essayAnswerId}`, body);
};

export const requestDeleteEssayAnswer = async (essayAnswerId) => {
  await loggedInUserInstance.delete(`/essay-answers/${essayAnswerId}`);
};

export const requestGetEssayAnswerList = async (quizId) => {
  const { data } = await anyoneInstance.get(`/quizzes/${quizId}/essay-answers`);

  return data;
};

export const requestGetQuizAsync = async (quizId) => {
  const { data } = await anyoneInstance.get(`/quizzes/${quizId}`);

  return data;
};

export const requestGetQuiz = (quizId: Number): AxiosPromise<AxiosResponse<Quiz>> => (
  anyoneInstance.get<AxiosResponse<Quiz>>(`/quizzes/${quizId}`)
);
