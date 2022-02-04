import { useState } from 'react';
import { getResponseData } from '../utils/response';

const useMutation = (callback, { onSuccess, onError, onFinish }) => {
  const [error, setError] = useState('');

  const mutate = async (data) => {
    try {
      const response = await callback(data);

      if (!response) {
        return;
      }

      if (!response.ok) {
        throw new Error(await response.text());
      }

      onSuccess?.(await getResponseData(response));
    } catch (error) {
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
