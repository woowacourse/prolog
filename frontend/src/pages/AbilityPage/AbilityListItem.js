import { useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useParams } from 'react-router-dom';

import Chip from '../../components/Chip/Chip';
import AddAbilityForm from './AddAbilityForm';
import EditAbilityForm from './EditAbilityForm';
import SubAbilityListItem from './SubAbilityListItem';

import { SubAbilityList, ArrowButton, EditingListItem, AbilityItem } from './styles';
import AbilityRequest from '../../apis/ability';
import AbilityManageButton from './Ability/AbilityManageButton';
import { ERROR_MESSAGE } from '../../constants';

const AbilityListItem = ({ ability, onAddAbility, onDelete, readOnly }) => {
  const { username: pageUsername } = useParams();
  const queryClient = useQueryClient();

  const { id, name, description, color, isParent, children: subAbilities } = ability;
  const [itemStatus, setItemStatus] = useState({
    isOpened: false,
    isEditing: false,
    isAddFormOpened: false,
  });

  const [addFormStatus, setAddFormStatus] = useState({
    name: '',
    description: '',
    color,
    parent: id,
  });

  useEffect(() => {
    setAddFormStatus({ name: '', description: '', color, parent: id });
  }, [itemStatus.isAddFormOpened, subAbilities.length]);

  const toggleIsOpened = () => {
    if (itemStatus.isOpened) {
      closeAddForm();
    }

    setItemStatus((prevState) => ({ ...prevState, isOpened: !prevState.isOpened }));
  };

  const closeAddForm = () => {
    setItemStatus((prevState) => ({
      ...prevState,
      isAddFormOpened: false,
    }));
  };

  const setEditStatus = (status) => {
    setItemStatus((prevState) => ({ ...prevState, isEditing: status }));
  };

  const onDeleteAbility = (id) => {
    const { isLoading, mutate } = onDelete;

    if (subAbilities) {
      alert('하위 역량이 있는 역량은 삭제할 수 없습니다.');
      return;
    }

    if (window.confirm('정말 삭제하시겠습니까?')) {
      if (!isLoading) mutate(id);
    }
  };

  const onAddChildAbility = () => {
    setItemStatus((prevState) => ({ ...prevState, isOpened: true, isAddFormOpened: true }));
  };

  /** 역량 수정 */
  const onEditAbility = useMutation(
    (formData) =>
      AbilityRequest.updateAbility({ url: `/abilities/${formData.id}`, data: formData }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([`${pageUsername}-abilities`]);
        setEditStatus(false);
      },
      onError: (errorData) => {
        const errorCode = errorData?.code;

        alert(ERROR_MESSAGE[errorCode] ?? '역량 수정을 할 수 없습니다. 잠시후 다시 시도해주세요.');
      },
    }
  );

  const onFormDataChange = (key) => (event) => {
    setAddFormStatus({ ...addFormStatus, [key]: event.target.value });
  };

  return (
    <>
      {itemStatus.isEditing ? (
        <EditingListItem isParent={true}>
          <EditAbilityForm
            id={id}
            name={name}
            color={color}
            description={description}
            isParent={isParent}
            onClose={() => setEditStatus(false)}
            onEdit={onEditAbility}
            readOnly={readOnly}
          />
        </EditingListItem>
      ) : (
        <AbilityItem>
          <ArrowButton
            isParent={isParent}
            isOpened={itemStatus.isOpened}
            onClick={toggleIsOpened}
            disabled={!subAbilities?.length}
          />
          <Chip
            title={name}
            textAlign="left"
            maxWidth="140px"
            backgroundColor={color}
            fontSize="12px"
          >
            {name}
          </Chip>
          <p>{description}</p>
          {!readOnly && (
            <AbilityManageButton
              addEvent={onAddChildAbility}
              updateEvent={() => setEditStatus(true)}
              deleteEvent={() => onDeleteAbility(id)}
            />
          )}
        </AbilityItem>
      )}

      {/* 새로운 자식 역량 추가 */}
      {itemStatus.isAddFormOpened && (
        <EditingListItem isParent={false}>
          <AddAbilityForm
            color={color}
            formData={addFormStatus}
            onFormDataChange={onFormDataChange}
            isParent={false}
            onClose={closeAddForm}
            onSubmit={onAddAbility}
          />
        </EditingListItem>
      )}

      {/* 자식역량 조회, 삭제, 수정 */}
      {!!subAbilities.length && (
        <SubAbilityList isOpened={itemStatus.isOpened}>
          {subAbilities.map(({ id, name, description, color }) => (
            <SubAbilityListItem
              key={id}
              id={id}
              name={name}
              description={description}
              color={color}
              onDelete={() => onDeleteAbility(id)}
              onEdit={onEditAbility}
              readOnly={readOnly}
            />
          ))}
        </SubAbilityList>
      )}
    </>
  );
};

export default AbilityListItem;
