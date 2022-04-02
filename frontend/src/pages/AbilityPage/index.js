import { useContext, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useQuery } from 'react-query';
import axios from 'axios';

import { UserContext } from '../../contexts/UserProvider';
import useAbility from '../../hooks/Ability/useAbility';
import useParentAbilityForm from '../../hooks/Ability/useParentAbilityForm';

import EmptyAbility from './Ability/EmptyAbility';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';
import ReportStudyLogTable from './StudyLogs/StudyLogTable';

import { COLOR } from '../../constants';
import { Container, AbilityList, EditingListItem, ListHeader, AddAbilityButton } from './styles';
import { BASE_URL } from '../../configs/environment';

const AbilityPage = () => {
  const { username } = useParams();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  const [page, setPage] = useState(1);

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

  /** 학습로그와 매핑된 역량 가져오기 */
  const { data: mappedStudyLogs = [] } = useQuery(
    [`${username}-ability-studylogs`, page],
    async () => {
      const { data } = await axios({
        method: 'get',
        url: `${BASE_URL}/members/${username}/ability-studylogs`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
        params: {
          size: 5,
          page: page,
        },
      });

      return { ...data };
    }
  );

  /** 학습로그 totalPage 가져오기 */
  const { data: studyLogs = [] } = useQuery([`${username}-studylogs`, page], async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/members/${username}/studylogs`,
      headers: {
        Authorization: `Bearer ${user.accessToken}`,
      },
      params: {
        size: 5,
        page: page,
      },
    });

    return { ...data };
  });

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
              saveButtonDisabled={!addFormStatus.name.trim() || !addFormStatus.color}
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

      <ReportStudyLogTable
        mappedStudyLogs={mappedStudyLogs}
        abilities={abilities}
        readOnly={readOnly}
        setPage={setPage}
        // 페이지네이션을 위한 studylogs
        studyLogs={studyLogs}
        totalSize={studyLogs.totalSize ?? 0}
      />
    </Container>
  );
};

export default AbilityPage;
