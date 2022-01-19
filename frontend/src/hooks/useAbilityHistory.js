import { useEffect, useState } from 'react';

const useAbilityHistory = ({ targetRef }) => {
  const [isModalOpened, setIsModalOpened] = useState(false);
  const [abilityHistories, setAbilityHistories] = useState([]);

  /** 역량 이력 모달 밖의 영역을 선택했을 때, 모달이 닫히도록 하는 기능 */
  useEffect(() => {
    const onCloseModal = ({ target }) => {
      if (!targetRef.current?.contains(target) && isModalOpened) {
        setIsModalOpened(false);
      }
    };

    document.addEventListener('click', onCloseModal);

    return () => {
      document.removeEventListener('click', onCloseModal);
    };
  }, [targetRef, isModalOpened]);

  window.addEventListener('keyup', (event) => {
    if (event.key !== 'Escape') return;

    setIsModalOpened(false);
  });

  /**
   * 역량 이력 가져오기 -> [{ 이력 id, 이력 title }]
   */
  const onShowAbilistyHistories = async () => {
    setIsModalOpened(true);
    setAbilityHistories([]);
  };

  return {
    isModalOpened,
    setIsModalOpened,
    abilityHistories,
    setAbilityHistories,
    onShowAbilistyHistories,
  };
};

export default useAbilityHistory;
