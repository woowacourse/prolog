import { Author } from './Studylogs';
import {Quiz} from "./Keywords";

export interface EssayAnswerRequest {
  quizId: number;
  answer: string;
}

export interface EssayAnswerResponse {
  id: number;
  quiz: Quiz;
  answer: string;
  author: Author;
  createdAt: string;
  updatedAt: string;
}
