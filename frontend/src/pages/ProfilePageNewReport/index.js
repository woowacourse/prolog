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

  const onSubmitReport = (event) => {
    event.preventDefault();

    alert('현재 준비중인 기능입니다.');

    // if (!studyLogs.length) {
    //   alert('최소 한 개 이상의 학습로그를 등록해주세요');
    // }

    // const data = {
    //   title: title !== '' ? title : `${new Date().toLocaleDateString()} ${nickname}의 리포트`,
    //   description,
    //   // abilityGraph: {
    //   //   abilities: [],
    //   // },
    //   posts: studyLogs,
    //   isRepresent: isMainReport,
    // };
  };

  const onRegisterMainReport = () => setIsMainReport((currentState) => !currentState);

  const onModalOpen = () => setIsModalOpened(true);
  const onModalClose = () => setIsModalOpened(false);

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        <h2>새 리포트 작성하기</h2>
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

        {/* 역량기능 부분 */}
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
          <Button size="X_SMALL" css={{ backgroundColor: `${COLOR.LIGHT_GRAY_400}` }} type="button">
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
