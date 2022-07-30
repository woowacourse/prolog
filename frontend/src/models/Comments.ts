import { Author } from './Studylogs';

export interface CommentType {
  id: number;
  member: Author;
  content: string;
  createAt: string;
}
