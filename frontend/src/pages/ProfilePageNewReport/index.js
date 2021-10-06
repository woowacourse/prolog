import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import localStorage from 'local-storage';

import { API, COLOR } from '../../constants';
import { Button } from '../../components';
import StudyLogModal from './StudyLogModal';
import ReportInfoInput from './ReportInfoInput';
import ReportStudyLogTable from './ReportStudyLogTable';
import { Checkbox, Form, FormButtonWrapper } from './style';
import { requestPostReport } from '../../service/requests';

const ProfilePageNewReport = () => {
  const { username } = useParams();
  const history = useHistory();

  const user = useSelector((state) => state.user.profile);
  const nickname = user.data?.nickname ?? username;
  const isLoggedIn = !!user.data;
  const accessToken = localStorage.get(API.ACCESS_TOKEN);

  useEffect(() => {
    if (isLoggedIn) {
      if (username !== user.data.username) {
        alert('본인의 리포트만 작성할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    } else {
      if (!accessToken) {
        alert('로그인한 사용자만 이용할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    }
  }, [isLoggedIn, username, user.data, history, accessToken]);

  const [isMainReport, setIsMainReport] = useState(false);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [studyLogs, setStudyLogs] = useState([]);

  const [isModalOpened, setIsModalOpened] = useState(false);

  const postNewReport = async (data) => {
    try {
      const response = await requestPostReport(data, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      history.push(`/${username}/reports`);
    } catch (error) {
      const errorCode = JSON.parse(error.message).code;
      if (errorCode === 4005) {
        alert('중복된 리포트 이름은 사용할 수 없습니다.');
      } else {
        console.error(error);
      }
    }
  };

  const onSubmitReport = (event) => {
    event.preventDefault();

    const data = {
      id: null,
      title: title !== '' ? title : `${new Date().toLocaleDateString()} ${nickname}의 리포트`,
      description,
      abilityGraph: { abilities: [] },
      studylogs: studyLogs.map((item) => ({ id: item.id, abilities: [] })),
      represent: false,
    };

    postNewReport(data);
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 작성을 취소하시겠습니까?')) {
      history.push(`/${username}/reports`);
    }
  };

  const onRegisterMainReport = () => setIsMainReport((currentState) => !currentState);

  const onModalOpen = () => setIsModalOpened(true);

  const onModalClose = () => setIsModalOpened(false);

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        <h2>새 리포트 작성하기</h2>
        {/* <div>
          <Checkbox
            type="checkbox"
            onChange={onRegisterMainReport}
            checked={isMainReport}
            id="main_report_checkbox"
            disabled
          />
          <label htmlFor="main_report_checkbox">대표 리포트로 지정하기</label>
        </div> */}

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
          <Button size="X_SMALL">리포트 등록</Button>
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

export default ProfilePageNewReport;
