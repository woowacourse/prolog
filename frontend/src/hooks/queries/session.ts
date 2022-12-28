import { useQuery } from 'react-query';
import { getSessionsByCurriculum } from '../../apis/session';

const QUERY_KEY = {
  sessions: 'sessions',
};

export const useGetSessions = (curriculumId: number) => {
  const { data } = useQuery([QUERY_KEY.sessions, curriculumId], () =>
    getSessionsByCurriculum(curriculumId)
  );

  return {
    sessions: data,
  };
};
