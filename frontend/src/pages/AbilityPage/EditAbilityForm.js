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

  const onSubmit = (event) => {
    event.preventDefault();

    onEdit({
      id,
      name: formData.name,
      description: formData.description,
      color: formData.color,
      onClose,
    });
  };

  /**
   * disableSaveButton 함수
   * 수정하고자 하는 역량의 정보가 이전과 동일하거나, 이름이 공백인 경우에는 등록이 불가능하다.
   *
   * @param {string} currentName - 현재의 수정하려고하는 역량 이름
   * @returns boolean || undefined
   */
  const disableSaveButton = () => {
    const { name: currentName, description: currentDesc, color: currentColor } = formData;

    if (!currentName.trim() || !currentColor.trim()) {
      return true;
    }

    if (currentName === name && currentDesc === description && currentColor === color) {
      return true;
    }

    return false;
  };

  return (
    <FormContainer>
      <div>
        <Chip
          title={name}
          textAlign="left"
          backgroundColor={formData.color}
          minWidth="3rem"
          fontSize="1.4rem"
        >
          {formData.name}
        </Chip>
      </div>
      <ListForm isParent={isParent} onSubmit={onSubmit}>
        <label>
          이름
          <input
            type="text"
            placeholder="이름"
            value={formData.name}
            onChange={onFormDataChange('name')}
            maxLength={60}
            required
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
              <input
                type="text"
                value={formData.color}
                onChange={onFormDataChange('color')}
                required
              />
            </ColorPicker>
          </label>
        )}

        <ManageButtonList>
          <Button
            type="button"
            color={COLOR.DARK_GRAY_900}
            fontSize="1.2rem"
            backgroundColor={COLOR.WHITE}
            borderColor={COLOR.DARK_BLUE_700}
            onClick={onClose}
          >
            취소
          </Button>
          <Button
            color={COLOR.WHITE}
            fontSize="1.2rem"
            backgroundColor={COLOR.DARK_BLUE_700}
            disabled={disableSaveButton()}
          >
            저장
          </Button>
        </ManageButtonList>
      </ListForm>
    </FormContainer>
  );
};

export default EditAbilityForm;
