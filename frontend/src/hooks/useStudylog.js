import { useState } from 'react';
import { ERROR_MESSAGE } from '../constants/message';
import {
  requestGetStudylog,
  requestGetStudylogs,
  requestDeleteStudylog,
  requestEditStudylog,
} from '../service/requests';
import useMutation from './useMutation';

const useStudylog = (defaultValue) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const onSuccess = (data) => {
    setResponse(data);
  };

  const onError = (error) => {
    console.error(error);
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
