import { useContext, useState } from 'react';

import { UserContext } from '../contexts/UserProvider';
import { ERROR_MESSAGE } from '../constants/message';
import {
  requestGetStudylog,
  requestGetStudylogs,
  requestDeleteStudylog,
  requestEditStudylog,
} from '../service/requests';
import useMutation from './useMutation';
import ERROR_CODE from '../constants/errorCode';

const useStudylog = (defaultValue, onSuccessHandler) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const { onLogout } = useContext(UserContext);

  const onSuccess = (data) => {
    setResponse(data);
    setError('');
    onSuccessHandler(data);
  };

  const onError = (error) => {
    console.error(error);

    if (error.code === ERROR_CODE.EXPIRED_ACCESS_TOKEN) {
      onLogout();
    }

    setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
  };

  const { mutate: getAllData } = useMutation(requestGetStudylogs, {
    onSuccess: (data) => {
      setResponse({
        ...data,
        data: data.data.map((item) => {
          const { read, scrap } = item;
          return {
            ...item,
            isRead: read,
            isScrapped: scrap,
          };
        }),
      });
    },
    onError,
  });

  const { mutate: getData } = useMutation(requestGetStudylog, {
    onSuccess,
    onError,
  });

  const { mutate: editData } = useMutation(requestEditStudylog, { onSuccess, onError });

  const { mutate: deleteData } = useMutation(requestDeleteStudylog, { onSuccess, onError });

  return { response, error, getAllData, getData, editData, deleteData };
};

export default useStudylog;
