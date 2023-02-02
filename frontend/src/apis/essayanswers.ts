import {client} from '.';
import {EssayAnswerRequest} from "../models/EssayAnswers";
import {AxiosPromise, AxiosResponse} from "axios";
import {httpRequester} from "./studylogs";
import {Quiz} from "../models/Keywords";

export const createNewEssayAnswerRequest = (body: EssayAnswerRequest) => client.post(`/essay-answers`, body);

export const requestGetQuiz = (quizId: Number): AxiosPromise<AxiosResponse<Quiz>> => httpRequester.get<AxiosResponse<Quiz>>(`/quizzes/${quizId}`);
