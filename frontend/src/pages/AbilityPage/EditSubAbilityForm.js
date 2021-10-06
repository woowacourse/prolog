import React, { useState } from 'react';
import { Chip } from '../../components';
import { COLOR } from '../../constants';
import {
  Button,
  FormContainer,
  ListForm,
  ManageButtonList,
  SubAbilityDescriptionInput,
  SubAbilityNameInput,
} from './styles';

const EditSubAbilityForm = ({ id, name, color, description, onClose, onEdit }) => {
  const [formData, setFormData] = useState({
    name,
    description,
  });

  const onFormDataChange = (key) => (event) => {
    setFormData({ ...formData, [key]: event.target.value });
  };

  const onSubmit = async (event) => {
    event.preventDefault();

    try {
      await onEdit({
        id,
        name: formData.name,
        description: formData.description,
        color: color,
      });
    } catch (error) {
      console.error(error);
    } finally {
      onClose();
    }
  };

  return (
    <FormContainer isParent={false}>
      <ListForm isParent={false} onSubmit={onSubmit}>
        <Chip title={name} backgroundColor={color} textAlign="left">
          <SubAbilityNameInput
            type="text"
            placeholder="이름"
            value={formData.name}
            onChange={onFormDataChange('name')}
            maxLength={60}
          />
        </Chip>

        <SubAbilityDescriptionInput
          type="text"
          placeholder="설명"
          value={formData.description}
          onChange={onFormDataChange('description')}
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
