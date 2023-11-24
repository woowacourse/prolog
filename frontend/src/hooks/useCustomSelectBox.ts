import { useEffect, useState } from 'react';

interface Params {
  targetRef: React.MutableRefObject<any>;
}

const useCustomSelectBox = ({ targetRef }: Params) => {
  const [isSelectBoxOpened, setIsSelectBoxOpened] = useState<boolean>(false);

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

  return { isSelectBoxOpened, setIsSelectBoxOpened };
};

export default useCustomSelectBox;
