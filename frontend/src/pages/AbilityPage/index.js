import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router';
import { COLOR } from '../../constants';
import {
  requestAddAbility,
  requestDeleteAbility,
  requestEditAbility,
  requestGetAbilities,
} from '../../service/requests';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';
import { abilityList } from './mockData';

import { Container, AbilityList, Button, EditingListItem, ListHeader, NoContent } from './styles';

const AbilityPage = () => {
  const history = useHistory();
  const { username } = useParams();

  const [abilities, setAbilities] = useState([]);
  const [addFormStatus, setAddFormStatus] = useState({
    isOpened: false,
    name: '',
    description: '',
    color: '#f6d7fe',
  });

  const accessToken = localStorage.getItem('accessToken');
  const user = useSelector((state) => state.user.profile);
  const isMine = user.data && username === user.data.username;

  const getData = async () => {
    try {
      const response = await requestGetAbilities(user.data.id, JSON.parse(accessToken));
      const data = await response.json();

      setAbilities(data);
    } catch (error) {
      console.error(error);
    }
  };

  const setAddFormIsOpened = (status) => () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: status }));
  };

  const addAbility = async ({ name, description, color, parent = null }) => {
    try {
      await requestAddAbility(JSON.parse(accessToken), { name, description, color, parent });
      await getData();
    } catch (error) {
      console.error(error);
    }
  };

  const deleteAbility = (id) => async () => {
    if (!window.confirm('삭제하시겠습니까?')) {
      return;
    }

    try {
      await requestDeleteAbility(JSON.parse(accessToken), id);
      await getData();
    } catch (error) {
      console.error(error);
    }
  };

  const editAbility = async ({ id, name, description, color }) => {
    try {
      await requestEditAbility(JSON.parse(accessToken), { id, name, description, color });
      await getData();
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (user.data?.id) {
      getData();
    }
  }, [user]);

  if (user.data && !isMine) {
    alert('잘못된 접근입니다.');
    history.push(`/${username}`);
  }

  return (
    <Container>
      <div>
        <h2>역량</h2>
        <Button
          type="button"
          backgroundColor={COLOR.LIGHT_GRAY_50}
          onClick={setAddFormIsOpened(true)}
        >
          역량 추가 +
        </Button>
      </div>
      {addFormStatus.isOpened && (
        <AbilityList>
          <EditingListItem isParent={true}>
            <AddAbilityForm
              name={addFormStatus.name}
              color={addFormStatus.color}
              description={addFormStatus.description}
              isParent={true}
              setIsFormOpened={setAddFormIsOpened}
              onClose={setAddFormIsOpened(false)}
              onSubmit={addAbility}
            />
          </EditingListItem>
        </AbilityList>
      )}
      <AbilityList>
        <ListHeader>
          <div>
            역량<span>{`(총 ${abilities?.length}개)`}</span>
          </div>
        </ListHeader>
        {!abilityList?.length && <NoContent>역량이 없습니다. 역량을 추가해주세요.</NoContent>}
        {abilities
          ?.filter(({ isParent }) => isParent)
          .map(({ id, name, description, color, isParent, children }) => (
            <AbilityListItem
              key={id}
              id={id}
              name={name}
              description={description}
              color={color}
              isParent={isParent}
              subAbilities={children}
              onDelete={deleteAbility}
              onAdd={addAbility}
              onEdit={editAbility}
            />
          ))}
      </AbilityList>
    </Container>
  );
};

export default AbilityPage;
