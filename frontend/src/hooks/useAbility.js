import { useState } from 'react';

const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#000000',
  parent: null,
};

const useAbility = (studylogs) => {
  const [abilities, setAbilities] = useState([]);
  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);

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

    makeNewAbility(newAbility);
    setAddFormStatus(DEFAULT_ABILITY_FORM);
    addFormClose();
  };

  return {
    addFormStatus,
    setAddFormStatus,
    onAddFormSubmit,
    addFormOpen,
    addFormClose,
    abilities,
  };
};

export default useAbility;
