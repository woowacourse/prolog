import React, { useState } from 'react';
import { Chip } from '../../components';
import { COLOR } from '../../constants';
import { Button, FormContainer, ListForm, ManageButtonList } from './styles';

const EditSubAbilityForm = ({ id, name, color, description, onClose, onEdit }) => {
  const [formData, setFormData] = useState({
    name,
    description,
  });

  const onFormDataChange = (key) => (event) => {
    setFormData({ ...formData, [key]: event.target.value });
  };

  return (
    <FormContainer isParent={false}>
      <ListForm
        isParent={false}
        onSubmit={async (event) => {
          event.preventDefault();

          console.log(id);
          await onEdit({
            id,
            name: formData.name,
            description: formData.description,
            color: color,
          });

          onClose();
        }}
      >
        <Chip backgroundColor={color}>
          <input
            type="text"
            placeholder="이름"
            value={formData.name}
            onChange={onFormDataChange('name')}
            style={{
              backgroundColor: '#ffffffcc',
              border: 'none',
            }}
          />
        </Chip>

        <input
          type="text"
          placeholder="설명"
          value={formData.description}
          onChange={onFormDataChange('description')}
          style={{ width: 'calc(100% - 1.4rem)' }}
        />

        <ManageButtonList>
          <Button
            type="button"
            backgroundColor={COLOR.WHITE}
            color={COLOR.DARK_GRAY_900}
            borderColor={COLOR.DARK_BLUE_700}
            onClick={onClose}
          >
            취소
          </Button>
          <Button backgroundColor={COLOR.DARK_BLUE_700} color={COLOR.WHITE}>
            저장
          </Button>
        </ManageButtonList>
      </ListForm>
    </FormContainer>
  );
};

export default EditSubAbilityForm;
