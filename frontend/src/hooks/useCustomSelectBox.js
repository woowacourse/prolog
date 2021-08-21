import { useEffect, useState } from 'react';

const useCustomSelectBox = ({ targetRef }) => {
  const [isSelectBoxOpened, setIsSelectBoxOpened] = useState(false);

  useEffect(() => {
    const onCloseOptionList = (event) => {
      if (!isSelectBoxOpened) return;

      event.preventDefault();

      if (!targetRef.current?.contains(event.target)) {
        setIsSelectBoxOpened(false);
      }
    };

    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
  }, [isSelectBoxOpened, targetRef]);

  return [isSelectBoxOpened, setIsSelectBoxOpened];
};

export default useCustomSelectBox;
