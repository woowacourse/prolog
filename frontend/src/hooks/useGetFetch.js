import { useState, useEffect } from 'react';

const BASE_URL = process.env.REACT_APP_API_URL;

const fetchURL = {
  getAllData: '/posts',
  getData: `/posts`,
};

const useGetFetch = (defaultValue, type, id) => {
  const [response, setResponse] = useState(defaultValue);
  const [error, setError] = useState('');

  const fetchData = async () => {
    try {
      const response = await fetch(`${BASE_URL}${fetchURL[type]}${id ? '/' + id : ''}`);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const json = await response.json();

      setResponse(json);
    } catch (error) {
      console.error(error);
      setError(error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return { response, error };
};

export default useGetFetch;
