import { useState } from 'react';

import useAbilityValidator from './useAbiliityValidator';

import { CONFIRM_MESSAGE } from '../constants';

//TODO : 자식 추가하기

const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#000000',
  parent: null,
};

const useAbility = () => {
  const [abilities, setAbilities] = useState([]);
  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);

  const { abilityValidate } = useAbilityValidator();

  const addFormClose = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: false }));
  };

  const addFormOpen = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  const makeNewAbility = ({ name, description, color }) => {
    const newAbility = {
      id: name,
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

    if (!abilityValidate(abilities, newAbility)) return;

    makeNewAbility(newAbility);
    setAddFormStatus(DEFAULT_ABILITY_FORM);
    addFormClose();
  };

  const onDelete = (id) => () => {
    if (window.confirm(CONFIRM_MESSAGE.DELETE_ABILITY)) {
      setAbilities((prevAbilities) => prevAbilities.filter((ability) => ability.id !== id));
    }
  };

  const onEdit = ({ id, name, description, color, onClose }) => {
    const targetAbilities = abilities.filter((ability) => ability.id !== id);
    const targetAbilityIndex = abilities.findIndex((ability) => ability.id === id);
    const newAbility = {
      id: name,
      name,
      description,
      color,
      isParent: true,
      children: null,
    };

    if (!abilityValidate(targetAbilities, newAbility)) return;

    setAbilities([
      ...abilities.slice(0, targetAbilityIndex),
      newAbility,
      ...abilities.slice(targetAbilityIndex + 1),
    ]);
    onClose();
  };

  return {
    addFormStatus,
    setAddFormStatus,
    onAddFormSubmit,
    onDelete,
    onEdit,
    addFormOpen,
    addFormClose,
    abilities,
  };
};

export default useAbility;
