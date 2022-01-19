import { useRef, useState } from 'react';
import { useParams } from 'react-router-dom';

import { useSelector } from 'react-redux';
import useRequest from '../../hooks/useRequest';
import useSnackBar from '../../hooks/useSnackBar';
import useAbility from '../../hooks/useAbility';
import useAbilityHistory from '../../hooks/useAbilityHistory';

import { requestGetAbilities } from '../../service/requests';
import AbilityListItem from './AbilityListItem';
import AddAbilityForm from './AddAbilityForm';
import { Button as FormButton } from '../../components';
import AbilityHistoryList from '../../components/Lists/AbilityHistoryList';
import ReportStudyLogTable from './StudyLogTable';
import StudyLogModal from './StudyLogModal';

import { COLOR } from '../../constants';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';

import {
  Container,
  AbilityList,
  Button,
  EditingListItem,
  ListHeader,
  FormButtonWrapper,
  HeaderContainer,
  AbilityHistoryContainer,
} from './styles';
import { TableButtonWrapper } from './StudyLogTable.styles';

const AbilityPage = () => {
  const { username } = useParams();
  const $abilityHistory = useRef(null);

  const user = useSelector((state) => state.user.profile);
  const readOnly = username !== user?.data?.username;

  const { isSnackBarOpen, SnackBar, openSnackBar } = useSnackBar();
  const {
    abilities,
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

          {!readOnly && (
            <TableButtonWrapper>
              <Button type="button" borderColor={COLOR.DARK_GRAY_800} onClick={addFormOpen}>
                역량 추가하기
              </Button>
            </TableButtonWrapper>
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
            .map((ability, index) => (
              <AbilityListItem
                key={index}
                ability={ability}
                // addAbility={}
                onEdit={onEditAbility}
                onDelete={onDeleteAbility}
                readOnly={false}
                readOnly={readOnly}
              />
            ))}
        </AbilityList>
      </Container>

      <ReportStudyLogTable
        onModalOpen={onReportModalOpen}
        studyLogs={studyLogs}
        setStudyLogs={setStudyLogs}
        abilities={abilities}
        readOnly={readOnly}
      />

      {!readOnly && (
        <FormButtonWrapper>
          <FormButton size="X_SMALL">저장</FormButton>
        </FormButtonWrapper>
      )}

      {!readOnly && isReportModalOpened && (
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
