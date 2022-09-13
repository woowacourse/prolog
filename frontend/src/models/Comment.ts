import { Author } from './Studylogs';

export interface CommentType {
  id: number;
  author: Author;
  content: string;
  createdAt: string;
}

export interface CommentListResponse {
  data: CommentType[];
}

export interface CommentRequest {
  content: string;
}
