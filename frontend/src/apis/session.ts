import { Session } from '../models/Studylogs';
import { client } from './index';

export interface SessionResponse {
  name: string;
  sessions: Session[];
}

export const getSessionsByCurriculum = async (curriculumId: number) => {
  const response = await client.get<SessionResponse>(`/curriculums/${curriculumId}/sessions`);

  return response.data.sessions;
};
