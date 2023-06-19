import { useState, useEffect } from 'react';
import { ERROR_MESSAGE } from '../constants';
import ERROR_CODE from '../constants/errorCode';
import { getResponseData } from '../utils/response';

const useRequest = (defaultValue, callback, onSuccess, onError, onFinish) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const fetchData = async (data) => {
    try {
      const response = await callback(data);

      if (!response) {
        return;
      }

      if (response.status >= 400) {
        throw new Error(response.statusText);
      }

      const responseData = await getResponseData(response);

      setResponse(responseData);
      onSuccess?.(responseData);
    } catch (error) {
      if (error instanceof TypeError) {
        setError(ERROR_CODE.SERVER_ERROR);
        onError?.({
          code: ERROR_CODE.SERVER_ERROR,
          message: ERROR_MESSAGE[ERROR_CODE.SERVER_ERROR],
        });
        return;
      }

      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(errorResponse.code);
      onError?.({ code: errorResponse.code, message: errorResponse.message });
    } finally {
      onFinish?.();
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return { response, error, fetchData };
};

export default useRequest;
