import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { useHistory } from 'react-router-dom';
import {
  createNewLevellogRequest,
  requestDeleteLevellog,
  requestEditLevellog,
  requestGetLevellog,
  requestGetLevellogs,
  requestGetRecentLevellogs,
} from '../../apis/levellogs';
import { ALERT_MESSAGE, PATH } from '../../constants';
import ERROR_CODE from '../../constants/errorCode';
import { LevellogRequest, LevellogResponse } from '../../models/Levellogs';
import useSnackBar from '../useSnackBar';

const QUERY_KEY = {
  levellogs: 'levellogs',
  levellog: 'levellog',
  recentLevellogs: 'recentLevellogs',
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

export const useGetRecentLevellogs = (size: number) => {
  return useQuery([QUERY_KEY.recentLevellogs], async () => {
    const response = await requestGetRecentLevellogs(size);

    return response.data;
  });
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

export const useGetLevellog = (
  { id },
  { onSuccess = (levellog: LevellogResponse) => {}, onError = () => {} } = {}
) => {
  const history = useHistory();
  const { openSnackBar } = useSnackBar();
  return useQuery<LevellogResponse>([QUERY_KEY.levellog, id], () => requestGetLevellog(id), {
    onSuccess: (levellog: LevellogResponse) => {
      onSuccess?.(levellog);
    },
    onError: (error) => {
      const { response } = (error as unknown) as AxiosError;

      if (response?.data.code === ERROR_CODE.NOT_EXIST_LEVELLOG) {
        openSnackBar(ALERT_MESSAGE.NO_EXIST_POST);
        history.push(PATH.LEVELLOG);
      }
    },
    refetchOnWindowFocus: false,
    retry: false,
  });
};

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
