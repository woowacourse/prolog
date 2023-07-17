import { useState, useEffect } from 'react';

const useFetch = (defaultValue, callback) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const fetchData = async () => {
    try {
      const response = await callback();
      const json = response.data;

      setResponse(json);
    } catch (error) {
      const errorResponse = error.response;

      console.error(errorResponse);
      setError(errorResponse);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return [response, error];
};

export default useFetch;
