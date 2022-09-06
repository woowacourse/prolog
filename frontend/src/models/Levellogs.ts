import { Author } from './Studylogs';

export interface LevellogRequest {
  title: string;
  content: string;
  levelLogs: QnAType[];
}

export interface LevellogResponse {
  id: number;
  title: string;
  content: string;
  levelLogs: QnAType[];
  author: Author;
  createdAt: string;
  updatedAt: string;
}

export interface QnAType {
  id?: number;
  question: string;
  answer: string;
}
