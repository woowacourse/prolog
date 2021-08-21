import { useEffect, useState } from 'react';

const useCustomSelectBox = ({ targetRef }) => {
  const [selectItems, setSelectItems] = useState(null);

  useEffect(() => {
    const onCloseOptionList = (e) => {
      if (!selectItems) return;
      e.preventDefault();

      if (!targetRef.current?.contains(e.target)) {
        setSelectItems(null);
      }
    };

    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
  }, [selectItems, targetRef]);

  return [selectItems, setSelectItems];
};

export default useCustomSelectBox;
