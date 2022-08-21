import { useEffect } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  createNewLevellogRequest,
  requestDeleteLevellog,
  requestEditLevellog,
  requestGetLevellog,
  requestGetLevellogs,
} from '../../apis/levellogs';
import { LevellogRequest, LevellogResponse } from '../../models/Levellogs';

const QUERY_KEY = {
  levellogs: 'levellogs',
  levellog: 'levellog',
};

export const useGetLevellogs = (currPage: number) => {
  const queryClient = useQueryClient();

  useEffect(() => {
    queryClient.prefetchQuery([QUERY_KEY.levellogs, currPage + 1], () =>
      requestGetLevellogs(currPage + 1)
    );
  }, [currPage]);

  return useQuery([QUERY_KEY.levellogs, currPage], () => requestGetLevellogs(currPage));
};

export const useCreateNewLevellogMutation = ({
  onSuccess = () => {},
  onError = (error: { code: number }) => {},
} = {}) =>
  useMutation((body: LevellogRequest) => createNewLevellogRequest(body), {
    onSuccess: () => {
      onSuccess?.();
    },
    onError: (error: { code: number }) => {
      onError?.(error);
    },
  });

export const useGetLevellog = ({ id }, { onSuccess = (levellog: LevellogResponse) => {} } = {}) =>
  useQuery<LevellogResponse>([QUERY_KEY.levellog, id], () => requestGetLevellog(id), {
    onSuccess: (levellog: LevellogResponse) => {
      onSuccess?.(levellog);
    },
    refetchOnWindowFocus: false,
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

export const useEditLevellogMutation = ({ id }, { onSuccess = () => {} } = {}) =>
  useMutation((body: LevellogRequest) => requestEditLevellog(id, body), {
    onSuccess: () => {
      onSuccess?.();
    },
  });
