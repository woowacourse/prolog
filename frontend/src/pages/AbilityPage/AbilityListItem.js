import { useEffect, useState } from 'react';
import Chip from '../../components/Chip/Chip';
import { COLOR } from '../../constants';

import AddAbilityForm from './AddAbilityForm';
import EditAbilityForm from './EditAbilityForm';
import { SubAbilityList, ManageButtonList, ArrowButton, Button, EditingListItem } from './styles';
import SubAbilityListItem from './SubAbilityListItem';

const AbilityListItem = ({ ability, addAbility, onEdit, onDelete }) => {
  const { id, name, description, color, isParent, children: subAbilities } = ability;
  const [itemStatus, setItemStatus] = useState({
    isOpened: false,
    isEditing: false,
    isAddFormOpened: false,
  });

  const [addFormStatus, setAddFormStatus] = useState({
    name: '',
    description: '',
    color,
    parent: id,
  });

  const toggleIsOpened = () => {
    setItemStatus((prevState) => ({ ...prevState, isOpened: !prevState.isOpened }));
  };

  const openSubList = () => {
    setItemStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  const closeAddForm = () => {
    setItemStatus((prevState) => ({
      ...prevState,
      isAddFormOpened: false,
    }));
  };

  const setIsAddFormOpened = (status) => () => {
    setItemStatus((prevState) => ({
      ...prevState,
      isAddFormOpened: status,
    }));
  };

  const setEditStatus = (status) => () => {
    setItemStatus((prevState) => ({ ...prevState, isEditing: status }));
  };

  useEffect(() => {
    if (!subAbilities.length) {
      setItemStatus((prevState) => ({ ...prevState, isOpened: false }));
    }
  }, [subAbilities.length]);

  const onAddFormSubmit = async (event) => {
    event.preventDefault();

    await addAbility({
      name: addFormStatus.name,
      description: addFormStatus.description,
      color: addFormStatus.color,
      parent: addFormStatus.parent,
    });

    setAddFormStatus({ ...addFormStatus, isOpened: false, name: '', description: '' });
    closeAddForm();
    openSubList();
  };

  const onFormDataChange = (key) => (event) => {
    setAddFormStatus({ ...addFormStatus, [key]: event.target.value });
  };

  return (
    <>
      {!itemStatus.isEditing && (
        <li key={id}>
          <ArrowButton
            isParent={isParent}
            isOpened={itemStatus.isOpened}
            onClick={toggleIsOpened}
            disabled={!subAbilities.length}
          ></ArrowButton>
          <Chip title={name} textAlign="left" maxWidth="140px" backgroundColor={color}>
            {name}
          </Chip>
          <p>{description}</p>
          <ManageButtonList>
            <Button
              type="button"
              backgroundColor={COLOR.DARK_BLUE_700}
              color={COLOR.WHITE}
              onClick={setIsAddFormOpened(true)}
            >
              추가
            </Button>
            <Button
              type="button"
              backgroundColor={COLOR.LIGHT_GRAY_200}
              color={COLOR.LIGHT_GRAY_900}
              onClick={setEditStatus(true)}
            >
              수정
            </Button>
            <Button
              type="button"
              backgroundColor={COLOR.RED_200}
              color={COLOR.RED_500}
              onClick={onDelete(id)}
            >
              삭제
            </Button>
          </ManageButtonList>
        </li>
      )}
      {itemStatus.isEditing && (
        <EditingListItem isParent={true}>
          <EditAbilityForm
            id={id}
            name={name}
            color={color}
            description={description}
            isParent={isParent}
            onClose={setEditStatus(false)}
            onEdit={onEdit}
          />
        </EditingListItem>
      )}
      {itemStatus.isAddFormOpened && (
        <EditingListItem isParent={true}>
          <AddAbilityForm
            color={color}
            formData={addFormStatus}
            onFormDataChange={onFormDataChange}
            isParent={false}
            onClose={setIsAddFormOpened(false)}
            onSubmit={onAddFormSubmit}
          />
        </EditingListItem>
      )}
      {!!subAbilities.length && (
        <SubAbilityList isOpened={itemStatus.isOpened}>
          {subAbilities.map(({ id, name, description, color }) => (
            <SubAbilityListItem
              key={id}
              id={id}
              name={name}
              description={description}
              color={color}
              onEdit={onEdit}
              onDelete={onDelete}
            />
          ))}
        </SubAbilityList>
      )}
    </>
  );
};

export default AbilityListItem;
