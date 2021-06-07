import { useState, useEffect } from 'react';

const useFetch = (defaultValue, callback) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const fetchData = async () => {
    try {
      const response = await callback();

      if (!response.ok) {
        throw new Error(response.status);
      }

      const json = await response.json();
      console.log(json);
      setResponse(json);
    } catch (error) {
      console.error(error);
      setError(error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return [response, error];
};

export default useFetch;
