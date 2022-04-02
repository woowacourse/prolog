import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import { Button } from '../../components';
import ReportInfo from '../ProfilePageNewReport/ReportInfo';
import { COLOR } from '../../constants';
import { Form, FormButtonWrapper } from '../ProfilePageNewReport/style';
import AbilityGraph from '../ProfilePageNewReport/AbilityGraph';

const mockAbilities = [
  {
    id: 1,
    name: '부모 역량 이름',
    description: '부모 역량 설명',
    color: '#001122',
    isParent: true,
    children: [
      {
        id: 2,
        name: '자식 역량 이름',
        description: '자식 역량 설명',
        color: '#001122',
        isParent: false,
      },
    ],
  },
  {
    id: 2,
    name: '부모 역량 이름2',
    description: '부모 역량 설명',
    color: '#FF2',
    isParent: true,
    children: [
      {
        id: 2,
        name: '자식 역량 이름',
        description: '자식 역량 설명',
        color: '#001122',
        isParent: false,
      },
    ],
  },
  {
    id: 3,
    name: '부모2 역량 이름',
    description: '부모2 역량 설명',
    color: '#554433',
    isParent: true,
    children: [
      {
        id: 4,
        name: '자식2 역량 이름',
        description: '자식2 역량 설명',
        color: '#001122',
        isParent: false,
      },
    ],
  },
];

const ProfilePageNewReport = () => {
  const history = useHistory();
  const { username } = useParams();
  const { user } = useContext(UserContext);

  const { isLoggedIn, accessToken } = user;

  const nickname = user.nickname ?? user.username;

  useEffect(() => {
    if (isLoggedIn) {
      if (username !== user.username) {
        alert('본인의 리포트만 작성할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    } else {
      if (!accessToken) {
        alert('로그인한 사용자만 이용할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    }
  }, [isLoggedIn, username, user.data, history]);

  const startDate = '';
  const endDate = '';
  const [title, setTitle] = useState('테스트 제목');
  const [description, setDescription] = useState('테스트 내용');

  const [abilities, setAbilities] = useState(
    mockAbilities.map(({ id, name, color }) => ({
      id,
      name,
      color,
      weight: 0,
    }))
  );

  const onSubmitReport = async (event) => {
    event.preventDefault();

    const data = {
      title,
      description,
      startDate,
      endDate,
      reportAbility: abilities.map(({ id, weight }) => ({ abilityId: id, weight })),
    };
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 수정을 취소하시겠습니까?')) {
      history.push(`/${username}/reports`);
    }
  };

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        <h2>새 리포트 작성하기</h2>

        <ReportInfo
          nickname={nickname}
          title={title}
          setTitle={setTitle}
          desc={description}
          setDescription={setDescription}
          edit={true}
        />

        <AbilityGraph abilities={abilities} setAbilities={setAbilities} edit={true} />

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
    </>
  );
};

export default ProfilePageNewReport;
