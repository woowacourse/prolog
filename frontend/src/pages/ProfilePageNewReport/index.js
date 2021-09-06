import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import Button from '../../components/Button/Button';

import { Desc, Form, InfoSection, Title } from './style';

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
  }, [isLoggedIn]);

  const [isMainReport, setIsMainReport] = useState(false);
  const [title, setTitle] = useState('');
  const [desc, setDesc] = useState('');

  const onSubmitReport = (event) => {
    event.preventDefault();
  };

  const onRegisterMainReport = () => setIsMainReport(!isMainReport);
  const onWriteTitle = ({ target: { value } }) => setTitle(value);
  const onWriteDesc = ({ target: { value } }) => setDesc(value);

  return (
    <Form onSubmit={onSubmitReport}>
      <div>
        <input type="checkbox" onChange={onRegisterMainReport} checked={isMainReport} />
        <label>대표 리포트로 지정하기</label>
      </div>

      <InfoSection>
        <label>✏️ Title</label>
        <Title
          placeholder="리포트 이름을 작성해주세요"
          required
          value={title}
          onChange={onWriteTitle}
        />

        <label>✏️ Description</label>
        <Desc
          placeholder="리포트에 대해서 간단히 소개해주세요."
          value={desc}
          onChange={onWriteDesc}
        />
      </InfoSection>

      {/* <section>역량영역</section> */}
      {/* <section>학습로그</section> */}
      <Button size="X_SMALL">등록</Button>
    </Form>
  );
};

export default ProfilePageNewReport;
