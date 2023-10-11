import { Quiz } from './Keywords';
import { Author } from './Studylogs';

export interface EssayAnswerRequest {
  quizId: number;
  answer: string;
}

export type EssayEditRequest = Pick<EssayAnswerRequest, 'answer'>;

export interface EssayAnswerResponse {
  id: number;
  quiz: Quiz;
  answer: string;
  author: Author;
  createdAt: string;
  updatedAt: string;
}

export type EssayAnswerFilter = {
  curriculumId: number;
  keywordId?: number;
  quizIds?: number[];
  memberIds?: number[];
};

export type EssayAnswerListRequest = EssayAnswerFilter & {
  page?: number;
  size?: number;
};

export type EssayAnswerFilterRequest = {
  curriculumId: number;
  keywordId?: number;
  quizIds?: number[];
  memberIds?: number[];
  page?: number;
  size?: number;
};
