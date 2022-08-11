import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  requestPostScrap,
  requestDeleteScrap,
  requestDeleteLike,
  requestDeleteStudylog,
  requestPostLike,
} from '../../service/requests';
import useSnackBar from '../useSnackBar';
import {
  ALERT_MESSAGE,
  ERROR_MESSAGE,
  SNACKBAR_MESSAGE,
  SUCCESS_MESSAGE,
} from '../../constants/message';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';

const QUERY_KEY = {
  scrap: 'scrap',
  profile: 'profile',
  introduction: 'introduction',
};

export const useDeleteStudylogMutation = () => {
  const history = useHistory();
  const { openSnackBar } = useSnackBar();

  return useMutation(requestDeleteStudylog, {
    onSuccess() {
      openSnackBar(SUCCESS_MESSAGE.DELETE_STUDYLOG);
      history.push(PATH.STUDYLOG);
    },
    onError: (error: { code: number }) => {
      alert(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_DELETE_STUDYLOG);
    },
  });
};

// 추가 리팩토링 필요. 아래의 4개 훅에 있는
// getStudylog는 useStudylog에서 파생된 코드로서, useStudylog 커스텀훅의 의존성을 제거한 후
// getStudylog 함수를 invalidateQueries로 변경하는 것이 바람직함

export const usePostScrapMutation = ({ getStudylog }) => {
  const { openSnackBar } = useSnackBar();

  return useMutation(requestPostScrap, {
    onSuccess: async () => {
      await getStudylog();
      openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_SCRAP);
    },
  });
};

export const useDeleteScrapMutation = ({ getStudylog }) => {
  const { openSnackBar } = useSnackBar();

  return useMutation(requestDeleteScrap, {
    onSuccess: async () => {
      await getStudylog();
      openSnackBar(SNACKBAR_MESSAGE.DELETE_SCRAP);
    },
  });
};

export const usePostLikeMutation = ({ getStudylog }) => {
  const { openSnackBar } = useSnackBar();

  return useMutation(requestPostLike, {
    onSuccess: async () => {
      await getStudylog();
      openSnackBar(SNACKBAR_MESSAGE.SET_LIKE);
    },
    onError: () => openSnackBar(SNACKBAR_MESSAGE.ERROR_SET_LIKE),
  });
};

export const useDeleteLikeMutation = ({ getStudylog }) => {
  const { openSnackBar } = useSnackBar();

  return useMutation(requestDeleteLike, {
    onSuccess: async () => {
      await getStudylog();
      openSnackBar(SNACKBAR_MESSAGE.UNSET_LIKE);
    },
    onError: () => openSnackBar(SNACKBAR_MESSAGE.ERROR_UNSET_LIKE),
  });
};
