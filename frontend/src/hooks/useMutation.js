import { useState } from 'react';
import { ERROR_MESSAGE } from '../constants';
import ERROR_CODE from '../constants/errorCode';
import { getResponseData } from '../utils/response';

const useMutation = (callback, { onSuccess, onError, onFinish }) => {
  const [error, setError] = useState('');

  const mutate = async (data) => {
    try {
      const response = await callback(data);

      if (!response) {
        return;
      }

      onSuccess?.(await getResponseData(response));
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

  return { error, mutate };
};

export default useMutation;
