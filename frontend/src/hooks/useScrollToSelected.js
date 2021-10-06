import { useEffect } from 'react';

const useScrollToSelected = ({ container, dependency, options, selectedOption }) => {
  useEffect(() => {
    const target = container.current;
    if (!target) return;

    const scrollY =
      (target.scrollHeight / options.length) *
      options.indexOf(options.find((option) => option.name === selectedOption));
    target.scroll({ top: scrollY, behavior: 'smooth' });

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dependency]);
};

export default useScrollToSelected;
