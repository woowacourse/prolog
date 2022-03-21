import { useState } from 'react';
import AbilityManageButton from './Ability/AbilityManageButton';
import { ColorChip, FormContainer, ListForm } from './styles';

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
      await onEdit.mutate({
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
      <ListForm isParent={false} isEditing={true} onSubmit={onSubmit}>
        <ColorChip backgroundColor={color} />
        <input
          type="text"
          placeholder="이름"
          value={formData.name}
          onChange={onFormDataChange('name')}
          maxLength={60}
        />

        <input
          type="text"
          placeholder="설명"
          value={formData.description}
          onChange={onFormDataChange('description')}
        />

        <AbilityManageButton cancelEvent={onClose} save={true} />
      </ListForm>
    </FormContainer>
  );
};

export default EditSubAbilityForm;
