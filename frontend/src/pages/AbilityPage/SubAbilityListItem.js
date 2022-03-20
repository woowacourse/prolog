import { useState } from 'react';
import { Chip } from '../../components';
import SubCategoryIcon from '../../components/@shared/Icons/SubCategoryIcon';
import AbilityManageButton from './Ability/AbilityManageButton';
import EditSubAbilityForm from './EditSubAbilityForm';
import { EditingListItem } from './styles';

const SubAbilityListItem = ({ id, name, description, color, onEdit, onDelete, readOnly }) => {
  const [isEditing, setIsEditing] = useState(false);

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

      {!readOnly && (
        <AbilityManageButton
          updateEvent={() => setIsEditing(true)}
          deleteEvent={() => onDelete(id)}
        />
      )}
    </li>
  );
};

export default SubAbilityListItem;
