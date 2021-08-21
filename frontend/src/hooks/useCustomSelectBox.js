import { useEffect, useState } from 'react';

const useCustomSelectBox = ({ targetRef }) => {
  const [isSelectBoxOpen, setIsSelectBoxOpen] = useState(false);

  useEffect(() => {
    const onCloseOptionList = (event) => {
      event.preventDefault();

      if (!targetRef.current?.contains(event.target)) {
        setIsSelectBoxOpen(false);
      }
    };

    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
  }, [isSelectBoxOpen, targetRef]);

  return [isSelectBoxOpen, setIsSelectBoxOpen];
};

export default useCustomSelectBox;
