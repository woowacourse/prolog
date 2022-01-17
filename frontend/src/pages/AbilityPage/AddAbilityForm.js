import SubCategoryIcon from '../../components/@shared/Icons/SubCategoryIcon';
import Chip from '../../components/Chip/Chip';
import { COLOR } from '../../constants';
import {
  ManageButtonList,
  Button,
  FormContainer,
  ListForm,
  ColorPicker,
  ColorChip,
} from './styles';

const AddAbilityForm = ({
  formData,
  onFormDataChange,
  onClose,
  isParent,
  onSubmit,
  sabveButtondisabled,
}) => {
  const { name, description, color } = formData;

  return (
    <FormContainer>
      <div>
        {!isParent && <SubCategoryIcon width={32} />}
        <Chip
          title={name}
          textAlign="left"
          backgroundColor={color}
          minWidth="3rem"
          height="2.4rem"
          fontSize="1.4rem"
          maxLength={60}
        >
          {name || '라벨 미리보기'}
        </Chip>
      </div>

      <ListForm isParent={isParent} onSubmit={onSubmit}>
        {!isParent && <ColorChip backgroundColor={color} visibility="hidden" />}
        <label>
          이름
          <input
            type="text"
            placeholder="이름"
            value={name}
            maxLength={60}
            onChange={onFormDataChange('name')}
            required
          />
        </label>
        <label>
          설명
          <input
            type="text"
            placeholder="설명"
            value={description}
            onChange={onFormDataChange('description')}
          />
        </label>
        {isParent && (
          <label>
            색상
            <ColorPicker>
              <input type="color" value={color} onChange={onFormDataChange('color')} />
              <input type="text" value={color} onChange={onFormDataChange('color')} required />
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
            disabled={sabveButtondisabled}
          >
            저장
          </Button>
        </ManageButtonList>
      </ListForm>
    </FormContainer>
  );
};

export default AddAbilityForm;
