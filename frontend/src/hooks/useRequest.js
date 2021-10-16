import { useState, useEffect } from 'react';

const useRequest = (defaultValue, callback, onSuccess, onError, onFinish) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const fetchData = async () => {
    try {
      const response = await callback();

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const json = await response.json();

      setResponse(json);
      onSuccess?.(json);
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
