import { useState, useEffect } from 'react';
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

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const responseData = await getResponseData(response);

      setResponse(responseData);
      onSuccess?.(responseData);
    } catch (error) {
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
