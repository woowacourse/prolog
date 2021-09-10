import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { Button } from '../../components';
import COLOR from '../../constants/color';
import Modal from './Modal/Modal';
import ReportInfoInput from './ReportInfoInput';
import ReportStudyLogTable from './ReportStudyLogTable';

import { Checkbox, Form, FormButtonWrapper } from './style';

// TODO 1. 페이지 나갈 떄, 확인하는 기능 넣기
// TODO 2. 등록할 때 title이 없다면, 날짜 + 우테코 닉네임으로 해서 보내기
// TODO 3. 학습로그 한 개 이상 선택하도록 하기

const ProfilePageNewReport = () => {
  const { username } = useParams();
  const history = useHistory();

  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;

  useEffect(() => {
    if (isLoggedIn) {
      if (username !== user.data.username) {
        alert('본인의 리포트만 작성할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    }
  }, [isLoggedIn, username, user.data, history]);

  const [isMainReport, setIsMainReport] = useState(false);
  const [title, setTitle] = useState('');
  const [desc, setDesc] = useState('');
  const [isModalOpened, setIsModalOpened] = useState(false);

  const onSubmitReport = (event) => {
    event.preventDefault();
  };

  const onRegisterMainReport = () => setIsMainReport(!isMainReport);
  const onWriteTitle = ({ target: { value } }) => setTitle(value);
  const onWriteDesc = ({ target: { value } }) => setDesc(value);
  const onModalOpen = () => setIsModalOpened(true);
  const onModalClose = () => setIsModalOpened(false);

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        {/* <h1>새 리포트 작성하기</h1> */}
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
          nickname={user.data?.nickname}
          title={title}
          onWriteTitle={onWriteTitle}
          desc={desc}
          onWriteDesc={onWriteDesc}
        />

        {/* 역량기능 */}
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

        <ReportStudyLogTable onModalOpen={onModalOpen} />

        <FormButtonWrapper>
          <Button size="X_SMALL" css={{ backgroundColor: `${COLOR.LIGHT_GRAY_400}` }} type="button">
            취소
          </Button>
          <Button size="X_SMALL">리포트 등록</Button>
        </FormButtonWrapper>
      </Form>
      {isModalOpened && <Modal onModalClose={onModalClose} />}
    </>
  );
};

export default ProfilePageNewReport;
