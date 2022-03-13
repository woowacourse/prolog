import { useContext, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useMutation, useQuery } from 'react-query';
import axios from 'axios';

import useAbility from '../../hooks/useAbility';
import { UserContext } from '../../contexts/UserProvider';

import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';

import { COLOR } from '../../constants';
import { Container, AbilityList, EditingListItem, ListHeader, AddAbilityButton } from './styles';

const AbilityPage = () => {
  const { username } = useParams();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  // TODO: 역량 등록하기

  const [studyLogs, setStudyLogs] = useState([]);

  const {
    addFormStatus,
    setAddFormStatus,
    onAddFormSubmit,
    addFormOpen,
    addFormClose,
  } = useAbility(studyLogs);

  // TODO: 역량 이력 불러오기, API 레이어 분리
  const { data: abilities } = useQuery([`${username}-abilities`], async () => {
    const { data } = await axios({
      method: 'get',
      url: `http://localhost:5000/members/${username}/abilities`,
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
        url: `http://localhost:5000/abilities/${id}`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      }),
    {
      onSuccess: () => {
        alert('역량 삭제하였습니다.');
      },
      onError: () => {
        alert('역량 삭제에 실패하였습니다. 잠시후 다시 시도해주세요.');
      },
    }
  );

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

      {!readOnly && addFormStatus.isOpened && (
        <AbilityList>
          <EditingListItem isParent={true}>
            <AddAbilityForm
              formData={addFormStatus}
              onFormDataChange={onFormDataChange}
              isParent={true}
              onClose={addFormClose}
              onSubmit={onAddFormSubmit}
              saveButtondisabled={!addFormStatus.name.trim() || !addFormStatus.color}
            />
          </EditingListItem>
        </AbilityList>
      )}

      <AbilityList height="36rem">
        {abilities
          ?.filter(({ isParent }) => isParent)
          .map((ability) => (
            <AbilityListItem
              key={ability.id}
              ability={ability}
              onDelete={onDeleteAbility}
              readOnly={readOnly}
            />
          ))}
      </AbilityList>
    </Container>
  );
};

export default AbilityPage;
