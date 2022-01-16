import { useEffect, useRef, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { COLOR } from '../../constants';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';
import {
  ERROR_MESSAGE,
  SUCCESS_MESSAGE,
  CONFIRM_MESSAGE,
  ALERT_MESSAGE,
} from '../../constants/message';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import useSnackBar from '../../hooks/useSnackBar';
import {
  requestAddAbility,
  requestDeleteAbility,
  requestEditAbility,
  requestGetAbilities,
  requestGetAbilityHistories,
} from '../../service/requests';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';
import NoAbility from './NoAbility';
import { Button as FormButton } from '../../components';

import {
  Container,
  AbilityList,
  Button,
  EditingListItem,
  ListHeader,
  NoContent,
  FormButtonWrapper,
  HeaderContainer,
  AbilityHistoryContainer,
} from './styles';
import { isCorrectHexCode } from '../../utils/hexCode';
import AbilityHistory from '../../components/Lists/AbilityHistoryList';
import AbilityHistoryList from '../../components/Lists/AbilityHistoryList';
import ReportStudyLogTable from './ReportStudyLogTable';
import StudyLogModal from './StudyLogModal';
import { TableButtonWrapper } from './ReportStudyLogTable.styles';

const DEFAULT_ABILITY_FORM = {
  isOpened: false,
  name: '',
  description: '',
  color: '#f6d7fe',
  parent: null,
};

const MockAbility = [
  { id: 1, title: '2022-02-22 역량' },
  { id: 2, title: '2022-02-21 역량' },
  { id: 3, title: '2022-02-20 역량' },
  { id: 4, title: '2022-02-19 역량' },
  { id: 5, title: '2022-02-18 역량' },
  { id: 6, title: '2022-02-17 역량' },
  { id: 7, title: '2022-02-16 역량' },
];

const AbilityPage = () => {
  const history = useHistory();
  const { username } = useParams();
  const { isSnackBarOpen, SnackBar, openSnackBar } = useSnackBar();

  const abilityHistoryModalRef = useRef(null);

  const [abilities, setAbilities] = useState(null);
  const [addFormStatus, setAddFormStatus] = useState(DEFAULT_ABILITY_FORM);
  const [isModalOpened, setIsModalOpened] = useState(false);
  const [isReportModalOpened, setReportIsModalOpened] = useState(false);
  const [studyLogs, setStudyLogs] = useState([]);
  const [studyLogAbilities, setStudyLogAbilities] = useState([]);

  const accessToken = localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
  const user = useSelector((state) => state.user.profile);
  const isMine = user.data && username === user.data?.username;

  /** 역량 이력 모달 밖의 영역을 선택했을 때, 모달이 닫히도록 하는 기능 */
  useEffect(() => {
    const onCloseModal = ({ target }) => {
      if (isModalOpened && !abilityHistoryModalRef.current?.contains(target)) {
        setIsModalOpened(false);
      }
    };

    document.addEventListener('click', onCloseModal);

    return () => {
      document.removeEventListener('click', onCloseModal);
    };
  }, [isModalOpened]);

  window.addEventListener('keyup', (event) => {
    if (event.key !== 'Escape') return;

    setIsModalOpened(false);
  });

  const addFormClose = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: false }));
  };

  const addFormOpen = () => {
    setAddFormStatus((prevState) => ({ ...prevState, isOpened: true }));
  };

  const onReportModalOpen = () => setReportIsModalOpened(true);

  const onReportModalClose = () => setReportIsModalOpened(false);

  const { fetchData: getData } = useRequest(
    [],
    () => requestGetAbilities(username, JSON.parse(accessToken)),
    (data) => {
      setAbilities(data);
    }
  );

  const { mutate: addAbility } = useMutation(
    ({ name, description, color, parent = null }) =>
      requestAddAbility(JSON.parse(accessToken), {
        name,
        description,
        color,
        parent,
      }),
    () => {
      openSnackBar(SUCCESS_MESSAGE.CREATE_ABILITY);
      getData();
    },
    (error) => {
      openSnackBar(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  );

  const { mutate: deleteAbility } = useMutation(
    (id) => {
      return requestDeleteAbility(JSON.parse(accessToken), id);
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
      const response = await requestEditAbility(JSON.parse(accessToken), {
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

  // 역량 이력 가져오기
  // 역량 이력 -> [{ 이력 id, 이력 title }]
  const onShowAbilistyHistories = async () => {
    setIsModalOpened(true);
    // try {
    //   const response = await requestGetAbilityHistories(accessToken);
    //   if (!response.ok) {
    //     throw new Error(await response.text());
    //   }
    //   const json = await response.json();
    //   console.log(json);
    // } catch (error) {
    //   const errorResponse = JSON.parse(error.message);
    //   console.error(errorResponse);
    // }
  };

  return (
    <>
      <Container>
        <HeaderContainer>
          {/* <h2>역량 페이지</h2> */}
          <Button
            type="button"
            backgroundColor={COLOR.LIGHT_GRAY_50}
            borderColor={COLOR.LIGHT_GRAY_400}
            fontSize="1.2rem"
            onClick={onShowAbilistyHistories}
          >
            🕖 히스토리
          </Button>

          {isModalOpened && (
            <AbilityHistoryContainer ref={abilityHistoryModalRef}>
              <h3>역량 이력 {MockAbility.length}개</h3>
              <AbilityHistoryList list={MockAbility} />
            </AbilityHistoryContainer>
          )}
        </HeaderContainer>

        <ListHeader>
          <h3>
            📚 역량 <span>{`(총 ${abilities?.length ?? 0}개)`}</span>
          </h3>

          <TableButtonWrapper>
            <Button type="button" borderColor={COLOR.DARK_GRAY_800} onClick={addFormOpen}>
              역량 추가하기
            </Button>
          </TableButtonWrapper>
        </ListHeader>

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

        <AbilityList height="36rem">
          {abilities
            ?.filter(({ isParent }) => isParent)
            .map((ability) => (
              <AbilityListItem
                key={ability.id}
                ability={ability}
                addAbility={addAbility}
                onEdit={editAbility}
                onDelete={onDelete}
                readOnly={false}
              />
            ))}

          {/* {abilities && !abilities.length && (
            <NoContent>
              <NoAbility getData={getData} accessToken={accessToken} />
            </NoContent>
          )} */}
        </AbilityList>
      </Container>

      <ReportStudyLogTable
        onModalOpen={onReportModalOpen}
        studyLogs={studyLogs}
        setStudyLogs={setStudyLogs}
        abilities={abilities}
        studyLogAbilities={studyLogAbilities}
        setStudyLogAbilities={setStudyLogAbilities}
      />

      <FormButtonWrapper>
        <FormButton size="X_SMALL">저장</FormButton>
      </FormButtonWrapper>

      {isReportModalOpened && (
        <StudyLogModal
          onModalClose={onReportModalClose}
          username={username}
          studyLogs={studyLogs}
          setStudyLogs={setStudyLogs}
        />
      )}

      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

export default AbilityPage;
