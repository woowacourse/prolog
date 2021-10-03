import React, { useState } from 'react';
import { Chip } from '../../components';
import { COLOR } from '../../constants';
import EditSubAbilityForm from './EditSubAbilityForm';
import { Button, ManageButtonList } from './styles';

const SubAbilityListItem = ({ id, name, description, color, onEdit, onDelete }) => {
  const [isEditing, setIsEditing] = useState(false);

  return isEditing ? (
    <li key={id} style={{ gridTemplateColumns: '0.2fr 4fr' }}>
      <span>{`ㄴ`}</span>
      <EditSubAbilityForm
        id={id}
        name={name}
        description={description}
        color={color}
        onEdit={onEdit}
        onClose={() => setIsEditing(false)}
      />
    </li>
  ) : (
    <li key={id}>
      <span>{`ㄴ`}</span>
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
