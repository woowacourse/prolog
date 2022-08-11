import { useState } from 'react';

export const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#000000',
  isParent: false,
};

const useParentAbilityForm = () => {
  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);

  /** 부모 역량 추가 섹션 열기 */
  const addFormOpen = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  /** 부모 역량 추가 섹션 닫기 */
  const addFormClose = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: false }));
  };

  return { addFormStatus, setAddFormStatus, addFormClose, addFormOpen };
};

export default useParentAbilityForm;
