import { useQuery } from 'react-query';
import { getSessions } from '../../apis/session';

const QUERY_KEY = {
  sessions: 'sessions',
};

export const useGetSessions = () => {
  const { data } = useQuery([QUERY_KEY.sessions], () => getSessions());

  return {
    sessions: data,
  };
};
