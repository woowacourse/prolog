import { useState } from 'react';

import useSnackBar from './useSnackBar';
import { isCorrectHexCode } from '../utils/hexCode';
import { ERROR_MESSAGE } from '../constants';

const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#000000',
  parent: null,
};

const useAddAbility = () => {
  const { openSnackBar } = useSnackBar();
  const [abilities, setAbilities] = useState([]);
  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);

  const addFormClose = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: false }));
  };

  const addFormOpen = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  const AbilityNameValidate = ({ name, color }) => {
    if (!name) {
      openSnackBar(ERROR_MESSAGE.NEED_ABILITY_NAME);
      return;
    }

    if (!color) {
      openSnackBar(ERROR_MESSAGE.NEED_ABILITY_COLOR);
      return;
    }

    if (!isCorrectHexCode(color)) {
      openSnackBar(ERROR_MESSAGE.INVALID_ABILIT_COLOR);
      return;
    }
  };

  const makeNewAbility = ({ name, description, color }) => {
    const newAbility = {
      id: abilities.length === 0 ? 0 : abilities.length,
      name,
      description,
      color,
      isParent: true,
      children: null,
    };

    setAbilities((prevAbility) => [...prevAbility, newAbility]);
  };

  const onAddFormSubmit = (event) => {
    event.preventDefault();

    const newName = addFormStatus.name.trim();
    const newColor = addFormStatus.color.trim();

    const newAbility = {
      name: newName,
      description: addFormStatus.description,
      color: newColor,
      parent: addFormStatus.parent,
    };

    makeNewAbility(newAbility);
    AbilityNameValidate(newAbility);
    setAddFormStatus(DEFAULT_ABILITY_FORM);
    addFormClose();
  };

  return { addFormStatus, setAddFormStatus, onAddFormSubmit, addFormOpen, addFormClose, abilities };
};

export default useAddAbility;
