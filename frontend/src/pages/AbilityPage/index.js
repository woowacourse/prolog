import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import useSnackBar from '../../hooks/useSnackBar';
import {
  requestAddAbility,
  requestDeleteAbility,
  requestEditAbility,
  requestGetAbilities,
} from '../../service/requests';
import { UserContext } from '../../contexts/UserProvider';

import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';
import NoAbility from './NoAbility';

import { isCorrectHexCode } from '../../utils/hexCode';

import { COLOR } from '../../constants';
import {
  ERROR_MESSAGE,
  SUCCESS_MESSAGE,
  CONFIRM_MESSAGE,
  ALERT_MESSAGE,
} from '../../constants/message';

import { Container, AbilityList, Button, EditingListItem, ListHeader, NoContent } from './styles';

const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#f6d7fe',
  parent: null,
};

const AbilityPage = () => {
  const history = useHistory();
  const { username } = useParams();
  const { isSnackBarOpen, SnackBar, openSnackBar } = useSnackBar();

  const [abilities, setAbilities] = useState(null);
  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);

  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const isMine = username === user.username;

  const addFormClose = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: false }));
  };

  const addFormOpen = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  const { fetchData: getData } = useRequest(
    [],
    () => requestGetAbilities(username, accessToken),
    (data) => {
      setAbilities(data);
    }
  );

  const { mutate: addAbility } = useMutation(
    ({ name, description, color, parent = null }) =>
      requestAddAbility(accessToken, {
        name,
        description,
        color,
        parent,
      }),
    {
      onSuccess: () => {
        openSnackBar(SUCCESS_MESSAGE.CREATE_ABILITY);
        getData();
      },
      onError: () => (error) => {
        openSnackBar(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const { mutate: deleteAbility } = useMutation(
    (id) => {
      return requestDeleteAbility(accessToken, id);
    },
    () => {
      openSnackBar(SUCCESS_MESSAGE.DELETE_ABILITY);

      getData();
    },
    (error) => {
      openSnackBar(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  );

  const editAbility = async ({ id, name, description, color }) => {
    try {
      const response = await requestEditAbility(accessToken, {
        id,
        name,
        description,
        color,
      });

      if (!response.ok) {
        const json = await response.json();
        throw new Error(json.code);
      }

      openSnackBar(SUCCESS_MESSAGE.EDIT_ABILITY);
      await getData();
    } catch (error) {
      openSnackBar(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  };

  useEffect(() => {
    if (user.data?.id) {
      getData();
    }
  }, [user]);

  if (user.data && !isMine) {
    alert(ALERT_MESSAGE.ACCESS_DENIED);
    history.push(`/${username}`);
  }

  const onAddFormSubmit = async (event) => {
    event.preventDefault();

    const newName = addFormStatus.name.trim();
    const newColor = addFormStatus.color.trim();

    if (!newName) {
      openSnackBar(ERROR_MESSAGE.NEED_ABILITY_NAME);
      return;
    }

    if (!newColor) {
      openSnackBar(ERROR_MESSAGE.NEED_ABILITY_COLOR);
      return;
    }

    if (!isCorrectHexCode(newColor)) {
      openSnackBar(ERROR_MESSAGE.INVALID_ABILIT_COLOR);
      return;
    }

    await addAbility({
      name: newName,
      description: addFormStatus.description,
      color: addFormStatus.color,
      parent: addFormStatus.parent,
    });

    setAddFormStatus(DEFAULT_ABILITY_FORM);
    addFormClose();
  };

  const onFormDataChange = (key) => (event) => {
    setAddFormStatus({ ...addFormStatus, [key]: event.target.value });
  };

  const onDelete = (id) => () => {
    if (window.confirm(CONFIRM_MESSAGE.DELETE_ABILITY)) {
      deleteAbility(id);
    }
  };

  return (
    <Container>
      <div>
        <h2>역량</h2>
        <Button type="button" backgroundColor={COLOR.LIGHT_GRAY_50} onClick={addFormOpen}>
          역량 추가 +
        </Button>
      </div>

      {addFormStatus.isOpened && (
        <AbilityList>
          <EditingListItem isParent={true}>
            <AddAbilityForm
              formData={addFormStatus}
              onFormDataChange={onFormDataChange}
              isParent={true}
              onClose={addFormClose}
              onSubmit={onAddFormSubmit}
              sabveButtondisabled={!addFormStatus.name.trim() || !addFormStatus.color}
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

        {abilities
          ?.filter(({ isParent }) => isParent)
          .map((ability) => (
            <AbilityListItem
              key={ability.id}
              ability={ability}
              addAbility={addAbility}
              onEdit={editAbility}
              onDelete={onDelete}
            />
          ))}

        {abilities && !abilities.length && (
          <NoContent>
            <NoAbility getData={getData} accessToken={accessToken} />
          </NoContent>
        )}
      </AbilityList>

      {isSnackBarOpen && <SnackBar />}
    </Container>
  );
};

export default AbilityPage;
