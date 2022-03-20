import SubCategoryIcon from '../../components/@shared/Icons/SubCategoryIcon';
import Chip from '../../components/Chip/Chip';
import AbilityManageButton from './Ability/AbilityManageButton';
import { FormContainer, ListForm, ColorPicker } from './styles';

const AddAbilityForm = ({
  formData,
  onFormDataChange,
  onClose,
  isParent,
  onSubmit,
  saveButtonDisabled,
}) => {
  const { name, description, color, parent } = formData;

  const onSubmitAbility = (event) => {
    event.preventDefault();

    const newAbility = { name, description, color, parent: parent ?? null };

    onSubmit.mutate(newAbility);
    if (onSubmit.isSuccess) {
      onClose();
    }
  };

  return (
    <FormContainer>
      <div>
        {/* {!isParent && <SubCategoryIcon width={32} />} */}
        <Chip
          title={name}
          textAlign="left"
          backgroundColor={color}
          minWidth="3rem"
          fontSize="1.2rem"
          maxLength={30}
        >
          {name || '라벨 미리보기'}
        </Chip>
      </div>

      <ListForm isParent={isParent} onSubmit={onSubmitAbility}>
        <label>
          이름
          <input
            type="text"
            placeholder="이름"
            value={name}
            maxLength={30}
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

        <AbilityManageButton
          cancelEvent={onClose}
          save={true}
          disableSaveButton={saveButtonDisabled}
        />
      </ListForm>
    </FormContainer>
  );
};

export default AddAbilityForm;
