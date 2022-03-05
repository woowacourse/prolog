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
  // TODO: errorObj로 통일, 현재는 해당 메시지를 다른곳에서 이용하고 있어 별도로 생성함.
  const [error, setError] = useState('');
  const [errorObj, setErrorObj] = useState({});

  const onSuccess = (data) => {
    setResponse(data);
  };

  const onError = (error) => {
    console.error(error);

    setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    setErrorObj({ code: error.code, message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT })
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

  return { response, error, errorObj, getAllData, getData, editData, deleteData };
};

export default useStudylog;
