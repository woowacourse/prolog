import { useState } from 'react';
import Chip from '../../components/Chip/Chip';
import { COLOR } from '../../constants';
import { ManageButtonList, Button, FormContainer, ListForm, ColorPicker } from './styles';

const EditAbilityForm = ({ id, name, color, description, isParent, onClose, onEdit }) => {
  const [formData, setFormData] = useState({
    name,
    description,
    color,
  });

  const onFormDataChange = (key) => (event) => {
    setFormData({ ...formData, [key]: event.target.value });
  };

  return (
    <FormContainer>
      <div>
        <Chip backgroundColor={formData.color} minWidth="3rem" fontSize="1.4rem">
          {formData.name}
        </Chip>
      </div>
      <ListForm
        isParent={isParent}
        onSubmit={async (event) => {
          event.preventDefault();

          console.log(id);
          await onEdit({
            id,
            name: formData.name,
            description: formData.description,
            color: formData.color,
          });

          onClose();
        }}
      >
        <label>
          이름
          <input
            type="text"
            placeholder="이름"
            value={formData.name}
            onChange={onFormDataChange('name')}
          />
        </label>
        <label>
          설명
          <input
            type="text"
            placeholder="설명"
            value={formData.description}
            onChange={onFormDataChange('description')}
          />
        </label>
        {isParent && (
          <label>
            색상
            <ColorPicker>
              <input type="color" value={formData.color} onChange={onFormDataChange('color')} />
              <input type="text" value={formData.color} onChange={onFormDataChange('color')} />
            </ColorPicker>
          </label>
        )}

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

export default EditAbilityForm;
