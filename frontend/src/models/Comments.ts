import { Author } from './Studylogs';

export interface CommentType {
  id: number;
  author: Author;
  content: string;
  createAt: string;
}
