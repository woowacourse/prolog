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
    } catch (error) {
      console.error(error);
      history.push(`/${username}/reports`);
    }
  };

  useEffect(() => {
    getReport(reportId);
  }, []);

  const postNewReport = async (data) => {
    try {
      const response = await requestEditReport(data, reportId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      history.push(`/${username}/reports`);
    } catch (error) {
      console.error(error);
    }
  };

  const onSubmitReport = (event) => {
    event.preventDefault();

    const data = {
      id: reportId,
      title: title !== '' ? title : `${new Date().toLocaleDateString()} ${nickname}의 리포트`,
      description,
      abilityGraph: { abilities: [] },
      studylogs: studyLogs.map((item) => ({ id: item.id, abilities: [] })),
      represent: isMainReport,
    };

    postNewReport(data);
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 수정을 취소하시겠습니까?')) {
      history.push(`/${username}/reports`);
    }
  };

  const onRegisterMainReport = () => setIsMainReport((currentState) => !currentState);

  const onModalOpen = () => setIsModalOpened(true);

  const onModalClose = () => setIsModalOpened(false);

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

        <section
          style={{
            height: '25rem',
            border: '1px solid black',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          준비중인 기능입니다.
        </section>

        <ReportStudyLogTable
          onModalOpen={onModalOpen}
          studyLogs={studyLogs}
          setStudyLogs={setStudyLogs}
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
