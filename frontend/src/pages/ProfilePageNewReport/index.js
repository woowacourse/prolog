import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { useMutation } from 'react-query';

import { UserContext } from '../../contexts/UserProvider';
import { Button } from '../../components';
import ReportInfo from './ReportInfo';
import { COLOR, ERROR_MESSAGE } from '../../constants';
import { Form, FormButtonWrapper } from './style';
import AbilityGraph from './AbilityGraph';
import axios from 'axios';
import { BASE_URL } from '../../configs/environment';
import useAbility from '../../hooks/Ability/useAbility';

const ProfilePageNewReport = () => {
  const history = useHistory();

  const { username } = useParams();
  const { user } = useContext(UserContext);
  const { isLoggedIn, accessToken } = user;
  const nickname = user.nickname ?? user.username;

  const { abilities: abilityList, isLoading } = useAbility({
    username,
  });

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
  const [abilities, setAbilities] = useState([]);

  useEffect(() => {
    setAbilities(
      abilityList?.map(({ id, name, color }) => ({
        id,
        name,
        color,
        weight: 0,
      })) ?? []
    );
  }, [isLoading]);

  /** 리포트 등록 */
  const onAddReport = useMutation(
    async (reportData) => {
      const { data } = await axios({
        method: 'post',
        url: `${BASE_URL}/reports`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
        data: {
          ...reportData,
        },
      });

      return { ...data };
    },
    {
      onSuccess: () => {
        history.push(`/${username}/reports`);
      },
      onError: (errorData) => {
        const errorCode = errorData?.code;

        alert(
          ERROR_MESSAGE[errorCode] ?? '리포트 등록에 실패하였습니다. 잠시후 다시 시도해주세요.'
        );
      },
    }
  );

  const onSubmitReport = async (event) => {
    event.preventDefault();

    if (!startDate || !endDate) {
      alert('기간을 선택해주세요');
      return;
    }

    if (abilityList.length < 3) {
      alert('2개 이상의 역량을 등록해주세요.');
      return;
    }

    if (calculateWeight(abilities) !== 20) {
      alert('역량의 가중치의 합이 20이 되어야합니다.');
      return;
    }

    /** 리포트 등록 */
    onAddReport.mutate({
      title,
      description,
      startDate,
      endDate,
      reportAbility: abilities.map(({ id, weight }) => ({ abilityId: id, weight })),
    });
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
