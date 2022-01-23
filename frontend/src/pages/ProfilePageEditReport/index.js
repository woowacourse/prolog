import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import localStorage from 'local-storage';

import { requestEditReport, requestGetReport } from '../../service/requests';
import { API, COLOR, ERROR_MESSAGE, REPORT_DESCRIPTION } from '../../constants';

import { Button } from '../../components';
import StudylogModal from '../ProfilePageNewReport/StudylogModal';
import ReportInfoInput from '../ProfilePageNewReport/ReportInfoInput';
import ReportStudylogTable from '../ProfilePageNewReport/ReportStudylogTable';
import { Checkbox, Form, FormButtonWrapper } from '../ProfilePageNewReport/style';
import AbilityGraph from '../ProfilePageReports/AbilityGraph';
import { limitLetterLength } from '../../utils/validator';

const ProfilePageEditReport = () => {
  const { username, id: reportId } = useParams();
  const history = useHistory();

  const user = useSelector((state) => state.user.profile);
  const nickname = user.data?.nickname ?? username;
  const isLoggedIn = !!user.data;
  const accessToken = localStorage.get(API.ACCESS_TOKEN);

  const [isMainReport, setIsMainReport] = useState(false);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [Studylogs, setStudylogs] = useState([]);
  const [abilities, setAbilities] = useState([]);
  const [StudylogAbilities, setStudylogAbilities] = useState([]);
  const [isModalOpened, setIsModalOpened] = useState(false);

  useEffect(() => {
    if (isLoggedIn) {
      if (username !== user.data.username) {
        alert('본인의 리포트만 수정할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    } else {
      if (!accessToken) {
        alert('로그인한 사용자만 이용할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    }
  }, [isLoggedIn, username, user.data, history, accessToken]);

  const getReport = async (reportId) => {
    try {
      const response = await requestGetReport(reportId);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const report = await response.json();

      setIsMainReport(report.represent);
      setTitle(report.title);
      setDescription(report.description);
      setStudylogs(report.studylogs);
      setAbilities(report.abilityGraph.abilities);
      report.studylogs.forEach((reportStudylog) => {
        setStudylogAbilities((currStudylogAbilities) => [
          ...currStudylogAbilities,
          { id: reportStudylog.id, abilities: reportStudylog.abilities },
        ]);
      });
    } catch (error) {
      console.error(error);
      history.push(`/${username}/reports`);
    }
  };

  useEffect(() => {
    getReport(reportId);
  }, []);

  const postEditReport = async (data) => {
    if (!limitLetterLength(description, REPORT_DESCRIPTION.MAX_LENGTH)) {
      alert('리포트 설명은 150글자를 넘을 수 없습니다.');

      return;
    }

    try {
      const response = await requestEditReport(data, reportId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      history.push(`/${username}/reports/${reportId}`);
    } catch (error) {
      const errorCode = JSON.parse(error.message).code;

      if (ERROR_MESSAGE[errorCode]) {
        alert(ERROR_MESSAGE[errorCode]);
      } else {
        console.error(error);
      }
    }
  };

  const getCheckedAbility = (StudylogId) => {
    const targetStudylogAbility = StudylogAbilities.find(
      (StudylogAbility) => StudylogAbility.id === StudylogId
    )?.abilities;

    return targetStudylogAbility?.map((ability) => ability.id) ?? [];
  };

  const onSubmitReport = (event) => {
    event.preventDefault();

    const data = {
      id: reportId,
      title: title !== '' ? title : `${new Date().toLocaleDateString()} ${nickname}의 리포트`,
      description,
      abilityGraph: {
        abilities: abilities.map(({ id, weight, isPresent }) => ({ id, weight, isPresent })),
      },
      // studylogs: Studylogs.map((item) => ({ id: item.id, abilities: [] })),
      studylogs: Studylogs.map((item) => ({
        id: item.id,
        abilities: getCheckedAbility(item.id),
      })),
      represent: isMainReport,
    };

    postEditReport(data);
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 수정을 취소하시겠습니까?')) {
      history.push(`/${username}/reports/${reportId}`);
    }
  };

  const onRegisterMainReport = () => setIsMainReport((currentState) => !currentState);

  const onModalOpen = () => setIsModalOpened(true);

  const onModalClose = () => setIsModalOpened(false);

  const onChangeAbilities = (data) => {
    setAbilities(data);
  };

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        <h2>리포트 수정하기</h2>
        <div>
          <Checkbox
            type="checkbox"
            onChange={onRegisterMainReport}
            checked={isMainReport}
            id="main_report_checkbox"
          />
          <label htmlFor="main_report_checkbox">대표 리포트로 지정하기</label>
        </div>

        <ReportInfoInput
          nickname={nickname}
          title={title}
          setTitle={setTitle}
          desc={description}
          setDescription={setDescription}
        />

        <section>
          <AbilityGraph abilities={abilities} setAbilities={onChangeAbilities} mode="EDIT" />
        </section>

        <ReportStudylogTable
          onModalOpen={onModalOpen}
          Studylogs={Studylogs}
          setStudylogs={setStudylogs}
          abilities={abilities}
          StudylogAbilities={StudylogAbilities}
          setStudylogAbilities={setStudylogAbilities}
        />

        <FormButtonWrapper>
          <Button
            size="X_SMALL"
            css={{ backgroundColor: `${COLOR.LIGHT_GRAY_400}` }}
            type="button"
            onClick={onCancelWriteReport}
          >
            취소
          </Button>
          <Button size="X_SMALL">리포트 수정</Button>
        </FormButtonWrapper>
      </Form>

      {isModalOpened && (
        <StudylogModal
          onModalClose={onModalClose}
          username={username}
          Studylogs={Studylogs}
          setStudylogs={setStudylogs}
        />
      )}
    </>
  );
};

export default ProfilePageEditReport;
