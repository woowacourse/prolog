import { useEffect, useState } from 'react';

const useMediaQuery = (query: string) => {
  const [matches, setMatches] = useState(() => window.matchMedia(query).matches);

  useEffect(() => {
    window.matchMedia(query).addEventListener('change', (event) => {
      console.log(event);
      setMatches(event.matches);
    });
  }, []);

  return matches;
};

export default useMediaQuery;
