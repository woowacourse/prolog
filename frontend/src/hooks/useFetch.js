import { useState, useEffect } from 'react';
import { ERROR_MESSAGE } from '../constants/message';

const useFetch = (defaultValue, callback) => {
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
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);
      setError(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return [response, error];
};

export default useFetch;
