import { Session } from '../models/Studylogs';
import { client } from './index';

export interface SessionResponse {
  name: string;
  sessions: Session[];
}

export const getSessionsByCurriculum = async (id: number) => {
  const response = await client.get<SessionResponse>(`/curriculums/${id}/sessions`);

  return response.data.sessions;
};
