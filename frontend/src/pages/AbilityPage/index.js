import { useContext, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import axios from 'axios';

import { UserContext } from '../../contexts/UserProvider';

import EmptyAbility from './Ability/EmptyAbility';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';

import { COLOR } from '../../constants';
import { Container, AbilityList, EditingListItem, ListHeader, AddAbilityButton } from './styles';
import { BASE_URL } from '../../configs/environment';

const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#000000',
  isParent: null,
};

const AbilityPage = () => {
  const queryClient = useQueryClient();
  const { username } = useParams();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);

  const addFormClose = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: false }));
  };

  const addFormOpen = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  // TODO: 역량 이력 불러오기, API 레이어 분리
  const { data: abilities = [] } = useQuery([`${username}-abilities`], async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/members/${username}/abilities`,
      headers: {
        Authorization: `Bearer ${user.accessToken}`,
      },
    });
    return data;
  });

  // TODO: 역량 처리가 정말 이 위치에서 쓰여야하는 것인지 고민해보기 (부모가 정말 알아야하는 값인가?)
  const onDeleteAbility = useMutation(
    async (id) =>
      await axios({
        method: 'delete',
        url: `${BASE_URL}/abilities/${id}`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      }),
    {
      onSuccess: () => {
        addFormClose();
        queryClient.invalidateQueries([`${username}-abilities`]);
      },
      onError: () => {
        alert('역량 삭제에 실패하였습니다. 잠시후 다시 시도해주세요.');
      },
    }
  );

  // 역량 등록하기
  const onAddAbility = useMutation(
    async (ability) =>
      await axios({
        method: 'post',
        url: `${BASE_URL}/abilities`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
        data: { ...ability },
      }),
    {
      onSuccess: () => {
        setAddFormStatus({ ...DEFAULT_ABILITY_FORM, isOpened: true });
        queryClient.invalidateQueries([`${username}-abilities`]);
        addFormClose();
      },
      onError: () => {
        alert('역량 등록에 실패하였습니다.');
      },
    }
  );

  // TODO: 너무 많은 인터렉션이 일어나고 있음. 그래도 괜찮을지 고민해보기. ex) uncontrolled 방법
  const onFormDataChange = (key) => (event) => {
    setAddFormStatus({ ...addFormStatus, [key]: event.target.value });
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
              formData={addFormStatus}
              onFormDataChange={onFormDataChange}
              isParent={true}
              onClose={addFormClose}
              onSubmit={onAddAbility}
              saveButtondisabled={!addFormStatus.name.trim() || !addFormStatus.color}
            />
          </EditingListItem>
        </AbilityList>
      )}

      <AbilityList height="32rem">
        {abilities.length === 0 ? (
          readOnly ? (
            <span>등록된 역량이 없습니다.</span>
          ) : (
            <EmptyAbility user={user} />
          )
        ) : (
          abilities
            .filter(({ isParent }) => isParent)
            .map((ability) => (
              <AbilityListItem
                key={ability.id}
                ability={ability}
                onDelete={onDeleteAbility}
                readOnly={readOnly}
                onAddAbility={onAddAbility}
              />
            ))
        )}
      </AbilityList>
    </Container>
  );
};

export default AbilityPage;
