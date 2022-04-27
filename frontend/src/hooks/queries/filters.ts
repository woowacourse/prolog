import { UseQueryOptions, useQuery } from 'react-query';

import {
  requestGetMissions,
  requestGetSessions,
  requestGetTags,
  ResponseError,
} from '../../apis/studylogs';
import { Mission, Session, Tag } from '../../models/Studylogs';

export const useTags = () =>
  useQuery<Tag[], ResponseError>(
    ['tags'],
    async () => {
      const response = await requestGetTags();
      return response.data;
    },
    { initialData: [] }
  );

export const useMissions = () =>
  useQuery<Mission[], ResponseError>(
    ['missions'],
    async () => {
      const response = await requestGetMissions();
      return response.data;
    },
    { initialData: [] }
  );

export const useSessions = () =>
  useQuery<Session[], ResponseError>(
    ['sessions'],
    async () => {
      const response = await requestGetSessions();
      return response.data;
    },
    { initialData: [] }
  );
