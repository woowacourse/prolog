import { useState } from 'react';
import { Chip } from '../../components';
import SubCategoryIcon from '../../components/@shared/Icons/SubCategoryIcon';
import { COLOR } from '../../constants';
import EditSubAbilityForm from './EditSubAbilityForm';
import { Button, ManageButtonList, EditingListItem } from './styles';

const SubAbilityListItem = ({ id, name, description, color, onEdit, onDelete, readOnly }) => {
  const [isEditing, setIsEditing] = useState(false);

  const manageButton = (id) => {
    return (
      <ManageButtonList>
        <Button
          type="button"
          color={COLOR.BLACK_900}
          fontSize="1.2rem"
          backgroundColor={COLOR.LIGHT_GRAY_200}
          borderColor={COLOR.LIGHT_GRAY_200}
          onClick={() => setIsEditing(true)}
        >
          수정
        </Button>
        <Button
          type="button"
          color={COLOR.BLACK_900}
          fontSize="1.2rem"
          backgroundColor={COLOR.RED_200}
          borderColor={COLOR.RED_200}
          onClick={() => onDelete(id)}
        >
          삭제
        </Button>
      </ManageButtonList>
    );
  };

  return isEditing ? (
    <EditingListItem>
      <SubCategoryIcon />
      <EditSubAbilityForm
        id={id}
        name={name}
        description={description}
        color={color}
        onEdit={onEdit}
        onClose={() => setIsEditing(false)}
      />
    </EditingListItem>
  ) : (
    <li>
      <SubCategoryIcon />
      <Chip title={name} backgroundColor={color} textAlign="left" maxWidth="140px" fontSize="12px">
        {name}
      </Chip>
      <p>{description}</p>
      {!readOnly && manageButton(id)}
    </li>
  );
};

export default SubAbilityListItem;
