import { useState } from 'react';
import Chip from '../../components/Chip/Chip';
import { COLOR } from '../../constants';
import AddAbilityForm from './AddAbilityForm';
import EditAbilityForm from './EditAbilityForm';
import { SubAbilityList, ManageButtonList, ArrowButton, Button } from './styles';
import SubAbilityListItem from './SubAbilityListItem';

const AbilityListItem = ({
  id,
  name,
  description,
  color,
  isParent,
  subAbilities,
  onDelete,
  onAdd,
  onEdit,
}) => {
  const [itemStatus, setItemStatus] = useState({
    isOpened: false,
    isEditing: false,
    isAddFormOpened: false,
  });

  const toggleIsOpened = () => {
    setItemStatus((prevState) => ({ ...prevState, isOpened: !prevState.isOpened }));
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

  return (
    <>
      {!itemStatus.isEditing && (
        <li key={id}>
          <ArrowButton
            isParent={isParent}
            isOpened={itemStatus.isOpened}
            onClick={toggleIsOpened}
            disabled={!subAbilities.length}
          >
            {`>`}
          </ArrowButton>
          <Chip backgroundColor={color}>{name}</Chip>
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
        <li style={{ gridTemplateColumns: '1fr' }}>
          <EditAbilityForm
            id={id}
            name={name}
            color={color}
            description={description}
            isParent={isParent}
            onClose={setEditStatus(false)}
            onEdit={onEdit}
          />
        </li>
      )}
      {itemStatus.isAddFormOpened && (
        <li style={{ gridTemplateColumns: '1fr' }}>
          <AddAbilityForm
            color={color}
            setIsFormOpened={setIsAddFormOpened}
            onSubmit={onAdd}
            onClose={() => setItemStatus((prevState) => ({ ...prevState, isAddFormOpened: false }))}
            parentId={id}
          />
        </li>
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
