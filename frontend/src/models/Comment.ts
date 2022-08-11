import { Author } from './Studylogs';

export interface CommentType {
  id: number;
  author: Author;
  content: string;
  createAt: string;
}

export interface CommentListResponse {
  data: CommentType[];
}

export interface CommentRequest {
  content: string;
}
