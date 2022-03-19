import { useContext } from 'react';
import { useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import useAbility from '../../hooks/Ability/useAbility';
import useParentAbilityForm from '../../hooks/Ability/useParentAbilityForm';

import EmptyAbility from './Ability/EmptyAbility';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';

import { COLOR } from '../../constants';
import { Container, AbilityList, EditingListItem, ListHeader, AddAbilityButton } from './styles';

const AbilityPage = () => {
  const { username } = useParams();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  const {
    addFormStatus, //
    setAddFormStatus,
    addFormClose,
    addFormOpen,
  } = useParentAbilityForm();

  const {
    abilities, //
    onAddAbility,
    onDeleteAbility,
  } = useAbility({
    username,
    setAddFormStatus,
    addFormClose,
  });

  /**
   * 역량 추가할 때 controlled 방법으로 form 데이터를 저장한다.
   * 너무 많은 인터렉션이 일어나고 있음. uncontrolled 방법을 고려하기
   * 적은 양의 데이터라서 controlled 방법도 당장은 괜찮을지도..?
   */
  const onFormDataChange = (key) => (event) => {
    setAddFormStatus((prevState) => ({ ...prevState, [key]: event.target.value }));
  };

  const showEmptyAbility = () => {
    return readOnly ? <span>등록된 역량이 없습니다.</span> : <EmptyAbility user={user} />;
  };

  return (
    <Container>
      <ListHeader>
        <h3>📚 역량</h3>

        {!readOnly && (
          <AddAbilityButton type="button" borderColor={COLOR.DARK_GRAY_800} onClick={addFormOpen}>
            ✚ 역량 추가하기
          </AddAbilityButton>
        )}
      </ListHeader>

      {/* 부모역량 추가하기 */}
      {!readOnly && addFormStatus.isOpened && (
        <AbilityList>
          <EditingListItem isParent={true}>
            <AddAbilityForm
              isParent={true}
              formData={addFormStatus}
              onFormDataChange={onFormDataChange}
              onClose={addFormClose}
              onSubmit={onAddAbility}
              saveButtondisabled={!addFormStatus.name.trim() || !addFormStatus.color}
            />
          </EditingListItem>
        </AbilityList>
      )}

      <AbilityList height="32rem">
        {abilities.length === 0
          ? showEmptyAbility()
          : abilities
              .filter(({ isParent }) => isParent)
              .map((ability) => (
                <AbilityListItem
                  key={ability.id}
                  ability={ability}
                  onDelete={onDeleteAbility}
                  onAddAbility={onAddAbility}
                  readOnly={readOnly}
                />
              ))}
      </AbilityList>
    </Container>
  );
};

export default AbilityPage;
