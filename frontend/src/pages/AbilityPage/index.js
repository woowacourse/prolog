import { useRef, useState } from 'react';
import { useParams } from 'react-router-dom';

import { ERROR_MESSAGE, SUCCESS_MESSAGE, CONFIRM_MESSAGE } from '../../constants/message';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import useSnackBar from '../../hooks/useSnackBar';
import useAbility from '../../hooks/useAbility';
import useAbilityHistory from '../../hooks/useAbilityHistory';

import {
  requestAddAbility,
  requestDeleteAbility,
  requestGetAbilities,
} from '../../service/requests';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';
import NoAbility from './NoAbility';
import { Button as FormButton } from '../../components';
import AbilityHistoryList from '../../components/Lists/AbilityHistoryList';
import ReportStudyLogTable from './ReportStudyLogTable';
import StudyLogModal from './StudyLogModal';

import { COLOR } from '../../constants';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';

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
import { TableButtonWrapper } from './ReportStudyLogTable.styles';

// TODO : 다른 사람들에게는 Readonly로 보일 수 있도록 수정해야함.
const AbilityPage = () => {
  const { username } = useParams();
  const $abilityHistory = useRef(null);

  const { isSnackBarOpen, SnackBar, openSnackBar } = useSnackBar();
  const {
    abilities, //
    addFormStatus,
    setAddFormStatus,
    onAddFormSubmit,
    onDelete: onDeleteAbility,
    onEdit: onEditAbility,
    addFormOpen,
    addFormClose,
  } = useAbility();
  const {
    isModalOpened: isAbilityHistoryModalOpened,
    abilityHistories,
    onShowAbilistyHistories,
  } = useAbilityHistory({ targetRef: $abilityHistory });

  const [isReportModalOpened, setReportIsModalOpened] = useState(false);
  const [studyLogs, setStudyLogs] = useState([]);
  const [studyLogAbilities, setStudyLogAbilities] = useState([]);

  const accessToken = localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);

  const onReportModalOpen = () => setReportIsModalOpened(true);
  const onReportModalClose = () => setReportIsModalOpened(false);

  const { fetchData: getData } = useRequest(
    [],
    () => requestGetAbilities(username, JSON.parse(accessToken)),
    (data) => {
      // setAbilities(data);
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

  const onFormDataChange = (key) => (event) => {
    setAddFormStatus({ ...addFormStatus, [key]: event.target.value });
  };

  return (
    <>
      <Container>
        <HeaderContainer>
          <Button
            type="button"
            backgroundColor={COLOR.LIGHT_GRAY_50}
            borderColor={COLOR.LIGHT_GRAY_400}
            fontSize="1.2rem"
            onClick={onShowAbilistyHistories}
          >
            🕖 히스토리
          </Button>

          {isAbilityHistoryModalOpened && (
            <AbilityHistoryContainer ref={$abilityHistory}>
              <h3>역량 이력 {abilityHistories?.length}개</h3>
              <AbilityHistoryList list={abilityHistories} />
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
            .map((ability, index) => (
              <AbilityListItem
                key={index}
                ability={ability}
                addAbility={addAbility}
                onEdit={onEditAbility}
                onDelete={onDeleteAbility}
                readOnly={false}
              />
            ))}
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
