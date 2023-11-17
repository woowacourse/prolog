import { useContext } from 'react';
import { useQuery } from 'react-query';
import { getMembersForFilter } from '../../apis/filter';
import {
  requestGetMissions,
  requestGetSessions,
  requestGetTags,
  ResponseError,
} from '../../apis/studylogs';
import { UserContext } from '../../contexts/UserProvider';
import { Author, Mission, Session, Tag } from '../../models/Studylogs';

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

export const useGetMySessions = () => {
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

export const useGetMembers = () => {
  return useQuery<Author[]>(
    ['memberList'],
    async () => {
      const members = await getMembersForFilter();
      return members;
    },
    { initialData: [] }
  );
};
