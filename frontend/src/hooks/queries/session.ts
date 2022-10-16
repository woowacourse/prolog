import { useQuery } from 'react-query';
import { getSessions } from '../../apis/session';

const QUERY_KEY = {
  sessions: 'sessions',
};

export const useGetSessions = () => {
  return useQuery([QUERY_KEY.sessions], () => getSessions());
};
