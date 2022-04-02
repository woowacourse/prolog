import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import { Button } from '../../components';
import ReportInfo from './ReportInfo';
import { COLOR } from '../../constants';
import { Form, FormButtonWrapper } from './style';
import AbilityGraph from './AbilityGraph';

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

  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

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

    if (!startDate || !endDate) {
      alert('기간을 선택해주세요');
      return;
    }

    if (calculateWeight(abilities) !== 20) {
      alert('역량의 가중치의 합이 20이 되어야합니다.');
      return;
    }

    const data = {
      title,
      description,
      startDate,
      endDate,
      reportAbility: abilities.map(({ id, weight }) => ({ abilityId: id, weight })),
    };
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 작성을 취소하시겠습니까?')) {
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
          setStartDate={setStartDate}
          setEndDate={setEndDate}
        />

        <AbilityGraph abilities={abilities} setAbilities={setAbilities} />

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
    </>
  );
};

export default ProfilePageNewReport;

const calculateWeight = (abilities) => {
  return abilities.reduce((sum, ability) => (sum += Number(ability.weight)), 0);
};
