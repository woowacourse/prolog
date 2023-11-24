import { useState } from 'react';
import { NotFound } from '../components';

const useNotFound = () => {
  const [isNotFound, setNotFound] = useState(false);

  return { isNotFound, setNotFound, NotFound };
};

export default useNotFound;
