import { useQuery } from 'react-query';

import {
  requestGetMissions,
  requestGetSessions,
  requestGetTags,
  ResponseError,
} from '../../apis/studylogs';
import { Mission, Session, Tag } from '../../models/Studylogs';
import { useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';

export const useTags = () =>
  useQuery<Tag[], ResponseError>(
    ['tags'],
    async () => {
      const response = await requestGetTags();
      return response.data;
    },
    { initialData: [] }
  );

export const useMissions = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  return useQuery<Mission[], ResponseError>(
    ['missions'],
    async () => {
      const response = await requestGetMissions({ accessToken });
      return response.data;
    },
    { initialData: [] }
  );
};

export const useSessions = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  return useQuery<Session[], ResponseError>(
    ['sessions'],
    async () => {
      const response = await requestGetSessions({ accessToken });
      return response.data;
    },
    { initialData: [] }
  );
};
