import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import localStorage from 'local-storage';

import { requestEditReport, requestGetReport } from '../../service/requests';
import { API, COLOR } from '../../constants';

import { Button } from '../../components';
import StudyLogModal from '../ProfilePageNewReport/StudyLogModal';
import ReportInfoInput from '../ProfilePageNewReport/ReportInfoInput';
import ReportStudyLogTable from '../ProfilePageNewReport/ReportStudyLogTable';
import { Checkbox, Form, FormButtonWrapper } from '../ProfilePageNewReport/style';
import AbilityGraph from '../ProfilePageReports/AbilityGraph';
import { ERROR_MESSAGE } from '../../constants/message';

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
  const [studyLogs, setStudyLogs] = useState([]);
  const [abilities, setAbilities] = useState([]);
  const [studyLogAbilities, setStudyLogAbilities] = useState([]);
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
      setStudyLogs(report.studylogs);
      setAbilities(report.abilityGraph.abilities);
      report.studylogs.forEach((reportStudyLog) => {
        setStudyLogAbilities((currStudyLogAbilities) => [
          ...currStudyLogAbilities,
          { id: reportStudyLog.id, abilities: reportStudyLog.abilities },
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
    if (description.length >= 150) {
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

  const getCheckedAbility = (studyLogId) => {
    const targetStudyLogAbility = studyLogAbilities.find(
      (studyLogAbility) => studyLogAbility.id === studyLogId
    )?.abilities;

    return targetStudyLogAbility?.map((ability) => ability.id) ?? [];
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
      // studylogs: studyLogs.map((item) => ({ id: item.id, abilities: [] })),
      studylogs: studyLogs.map((item) => ({
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

        <ReportStudyLogTable
          onModalOpen={onModalOpen}
          studyLogs={studyLogs}
          setStudyLogs={setStudyLogs}
          abilities={abilities}
          studyLogAbilities={studyLogAbilities}
          setStudyLogAbilities={setStudyLogAbilities}
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
        <StudyLogModal
          onModalClose={onModalClose}
          username={username}
          studyLogs={studyLogs}
          setStudyLogs={setStudyLogs}
        />
      )}
    </>
  );
};

export default ProfilePageEditReport;
