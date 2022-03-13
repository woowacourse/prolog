import { useContext, useState } from 'react';
import axios from 'axios';
import { useMutation, useQueryClient } from 'react-query';
import { useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import Chip from '../../components/Chip/Chip';
import AddAbilityForm from './AddAbilityForm';
import EditAbilityForm from './EditAbilityForm';
import SubAbilityListItem from './SubAbilityListItem';

import { COLOR } from '../../constants';
import {
  SubAbilityList,
  ManageButtonList,
  ArrowButton,
  Button,
  EditingListItem,
  AbilityItem,
} from './styles';

const AbilityListItem = ({ ability, addAbility, onDelete, readOnly }) => {
  const { username: pageUsername } = useParams();
  const { user } = useContext(UserContext);
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

  const toggleIsOpened = () => {
    setItemStatus((prevState) => ({ ...prevState, isOpened: !prevState.isOpened }));
  };

  const openSubList = () => {
    setItemStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  const closeAddForm = () => {
    setItemStatus((prevState) => ({
      ...prevState,
      isAddFormOpened: false,
    }));
  };

  const setIsAddFormOpened = (status) => () => {
    setItemStatus((prevState) => ({
      ...prevState,
      isAddFormOpened: status,
    }));
  };

  const setEditStatus = (status) => {
    setItemStatus((prevState) => ({ ...prevState, isEditing: status }));
  };

  // useEffect(() => {
  //   if (!subAbilities.length) {
  //     setItemStatus((prevState) => ({ ...prevState, isOpened: false }));
  //   }
  // }, [subAbilities.length]);

  const onAddFormSubmit = async (event) => {
    event.preventDefault();

    await addAbility({
      name: addFormStatus.name,
      description: addFormStatus.description,
      color: addFormStatus.color,
      parent: addFormStatus.parent,
    });

    setAddFormStatus({ ...addFormStatus, isOpened: false, name: '', description: '' });
    closeAddForm();
    openSubList();
  };

  const onDeleteAbility = (id) => {
    const { isLoading, mutate } = onDelete;

    if (window.confirm('정말 삭제하시겠습니까?')) {
      if (!isLoading) mutate(id);
    }
  };

  // TODO: 역량 수정하기
  const onEditAbility = useMutation(
    async (formData) =>
      await axios({
        method: 'put',
        url: `http://localhost:5000/abilities/${formData.id}`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
        data: { ...formData },
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([`${pageUsername}-abilities`]);
        setEditStatus(false);
      },
      onError: () => {
        alert('역량 수정을 할 수 없습니다. 잠시후 다시 시도해주세요.');
      },
    }
  );

  const onFormDataChange = (key) => (event) => {
    setAddFormStatus({ ...addFormStatus, [key]: event.target.value });
  };

  const manageButton = (id) => {
    return (
      <ManageButtonList>
        <Button
          type="button"
          color={COLOR.BLACK_900}
          fontSize="1.2rem"
          borderColor={COLOR.DARK_GRAY_800}
          // onClick={setIsAddFormOpened(true)}
        >
          추가
        </Button>
        <Button
          type="button"
          color={COLOR.BLACK_900}
          fontSize="1.2rem"
          backgroundColor={COLOR.LIGHT_GRAY_200}
          borderColor={COLOR.LIGHT_GRAY_200}
          onClick={() => setEditStatus(true)}
        >
          수정
        </Button>
        <Button
          type="button"
          color={COLOR.BLACK_900}
          fontSize="1.2rem"
          backgroundColor={COLOR.RED_200}
          borderColor={COLOR.RED_200}
          onClick={() => onDeleteAbility(id)}
        >
          삭제
        </Button>
      </ManageButtonList>
    );
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
          {!readOnly && manageButton(id)}
        </AbilityItem>
      )}

      {/* 새로운 자식 역량 추가 */}
      {itemStatus.isAddFormOpened && (
        <EditingListItem isParent={true}>
          <AddAbilityForm
            color={color}
            formData={addFormStatus}
            onFormDataChange={onFormDataChange}
            isParent={false}
            onClose={() => setEditStatus(false)}
            onSubmit={onAddFormSubmit}
          />
        </EditingListItem>
      )}

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
