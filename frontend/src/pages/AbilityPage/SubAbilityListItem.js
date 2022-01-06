import { useState } from 'react';
import PropTypes from 'prop-types';

import SubCategoryIcon from '../../components/@shared/Icons/SubCategoryIcon';
import { Chip } from '../../components';
import EditSubAbilityForm from './EditSubAbilityForm';
import { Button, ManageButtonList, EditingListItem } from './styles';
import { COLOR } from '../../constants';

const SubAbilityListItem = ({ id, name, description, color, onEdit, onDelete, readOnly }) => {
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
      <Chip title={name} backgroundColor={color} textAlign="left" maxWidth="140px">
        {name}
      </Chip>
      <p>{description}</p>
      {!readOnly && (
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
      )}
    </li>
  );
};

export default SubAbilityListItem;

SubAbilityListItem.prototype = {
  id: PropTypes.number,
  name: PropTypes.string,
  description: PropTypes.string,
  color: PropTypes.string,
  onEdit: PropTypes.func,
  onDelete: PropTypes.func,
  readOnly: PropTypes.bool,
};
