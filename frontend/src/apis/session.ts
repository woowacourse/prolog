import { Session } from '../models/Studylogs';
import { client } from './index';

export const getSessions = async () => {
  const response = await client.get<Session[]>('/sessions');

  return response.data;
};
