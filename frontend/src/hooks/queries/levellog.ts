import { useMutation, useQuery } from 'react-query';
import {
  createNewLevellogRequest,
  requestDeleteLevellog,
  requestGetLevellog,
  requestGetLevellogs,
  requestPutLevellog,
} from '../../apis/levellogs';
import { LevellogRequest, LevellogResponse } from '../../models/Levellogs';

const QUERY_KEY = {
  levellogs: 'levellogs',
  levellog: 'levellog',
};

export const useGetLevellogs = () => useQuery(QUERY_KEY.levellogs, requestGetLevellogs);

export const useCreateNewLevellogMutation = ({ onSuccess = () => {} } = {}) =>
  useMutation((body: LevellogRequest) => createNewLevellogRequest(body), {
    onSuccess: () => {
      onSuccess?.();
    },
  });

export const useGetLevellog = ({ id }, { onSuccess = (levellog: LevellogResponse) => {} } = {}) =>
  useQuery<LevellogResponse>([QUERY_KEY.levellog, id], () => requestGetLevellog(id), {
    onSuccess: (levellog: any) => {
      onSuccess?.(levellog);
    },
  });

export const useDeleteLevellogMutation = (
  { id },
  { onSuccess = () => {}, onError = (error: { code: number }) => {} } = {}
) =>
  useMutation(() => requestDeleteLevellog(id), {
    onSuccess: () => {
      onSuccess?.();
    },
    onError: (error: { code: number }) => {
      onError?.(error);
    },
  });

export const usePutLevellogMutation = ({ id, body }, { onSuccess = () => {} } = {}) =>
  useMutation(() => requestPutLevellog(id, body), {
    onSuccess: () => {
      onSuccess?.();
    },
  });
