import { useMutation, useQuery } from 'react-query';
import { createNewLevellogRequest, requestGetLevellogs } from '../../apis/levellogs';
import { LevellogRequest } from '../../models/Levellogs';

const QUERY_KEY = {
  levellogs: 'levellogs',
};

export const useGetLevellogs = () => useQuery(QUERY_KEY.levellogs, requestGetLevellogs);

export const useCreateNewLevellog = ({ onSuccess = () => {} } = {}) =>
  useMutation((body: LevellogRequest) => createNewLevellogRequest(body), {
    onSuccess: () => {
      onSuccess?.();
    },
  });
