import React, { useState } from 'react';
import { Chip } from '../../components';
import SubCategoryIcon from '../../components/@shared/Icons/SubCategoryIcon';
import { COLOR } from '../../constants';
import EditSubAbilityForm from './EditSubAbilityForm';
import { Button, ManageButtonList, EditingListItem } from './styles';

const SubAbilityListItem = ({ id, name, description, color, onEdit, onDelete }) => {
  const [isEditing, setIsEditing] = useState(false);

  return isEditing ? (
    <EditingListItem key={id}>
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
    <li key={id}>
      <SubCategoryIcon />
      <Chip backgroundColor={color}>{name}</Chip>
      <p>{description}</p>
      <ManageButtonList>
        <Button
          type="button"
          backgroundColor={COLOR.LIGHT_GRAY_200}
          color={COLOR.LIGHT_GRAY_900}
          onClick={() => setIsEditing(true)}
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
  );
};

export default SubAbilityListItem;
