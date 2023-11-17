import type { Author } from './Studylogs';

interface FilterData {
  id: number;
  name: string;
}

export interface FilterResponse {
  sessions: FilterData[];
  missions: unknown[];
  tags: FilterData[];
  members: Author[];
}
