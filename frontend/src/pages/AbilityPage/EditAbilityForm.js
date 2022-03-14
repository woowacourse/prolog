import { useState } from 'react';
import Chip from '../../components/Chip/Chip';
import { COLOR, ERROR_MESSAGE } from '../../constants';
import { isCorrectHexCode } from '../../utils/hexCode';
import { ManageButtonList, Button, FormContainer, ListForm, ColorPicker } from './styles';

const EditAbilityForm = ({ id, name, color, description, isParent, onClose, onEdit }) => {
  const [formData, setFormData] = useState({
    id,
    name,
    description,
    color,
  });

  const onFormDataChange = (event, key) => {
    setFormData((prevData) => ({ ...prevData, [key]: event.target.value }));
  };

  const onSubmit = (event) => {
    event.preventDefault();
    const { isLoading, mutate } = onEdit;

    if (!formData.name.trim()) {
      alert(ERROR_MESSAGE.NEED_ABILITY_NAME);
      return;
    }

    if (!formData.color.trim()) {
      alert(ERROR_MESSAGE.NEED_ABILITY_COLOR);
      return;
    }

    if (!isCorrectHexCode(formData.color.trim())) {
      alert(ERROR_MESSAGE.INVALID_ABILIT_COLOR);
      return;
    }

    if (!isLoading) {
      mutate(formData);
    }
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
          fontSize="1.2rem"
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
            onChange={(event) => onFormDataChange(event, 'name')}
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
            onChange={(event) => onFormDataChange(event, 'description')}
          />
        </label>

        {isParent && (
          <label>
            색상
            <ColorPicker>
              <input
                type="color"
                fontSize="1.2rem"
                value={formData.color}
                onChange={(event) => onFormDataChange(event, 'color')}
              />
              <input
                type="text"
                fontSize="1.2rem"
                value={formData.color}
                onChange={(event) => onFormDataChange(event, 'color')}
                required
              />
            </ColorPicker>
          </label>
        )}

        <ManageButtonList>
          <Button
            type="button"
            fontSize="1.2rem"
            borderColor={COLOR.DARK_BLUE_700}
            backgroundColor={COLOR.WHITE}
            color={COLOR.DARK_GRAY_900}
            onClick={onClose}
          >
            취소
          </Button>
          <Button
            fontSize="1.2rem"
            backgroundColor={COLOR.DARK_BLUE_700}
            color={COLOR.WHITE}
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
