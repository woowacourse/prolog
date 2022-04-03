import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import { Button } from '../../components';
import ReportInfo from '../ProfilePageNewReport/ReportInfo';
import { COLOR, ERROR_MESSAGE } from '../../constants';
import { Form, FormButtonWrapper } from '../ProfilePageNewReport/style';
import AbilityGraph from '../ProfilePageNewReport/AbilityGraph';
import { useMutation, useQuery } from 'react-query';
import axios from 'axios';
import { BASE_URL } from '../../configs/environment';

const ProfilePageNewReport = () => {
  const history = useHistory();

  const { id, username } = useParams();
  console.log(id);
  const { user } = useContext(UserContext);
  const { isLoggedIn, accessToken } = user;
  const nickname = user.nickname ?? user.username;

  /** 리포트 목록 가져오기 */
  const { data: reportData = [], isLoading } = useQuery([`${username}-reports-${id}`], async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/reports/${id}`,
    });

    return data;
  });

  /** 리포트 등록 */
  const onEditReport = useMutation(
    async (reportData) => {
      const { data } = await axios({
        method: 'put',
        url: `${BASE_URL}/reports/${id}`,
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
          ERROR_MESSAGE[errorCode] ?? '리포트 수정에 실패하였습니다. 잠시후 다시 시도해주세요.'
        );
      },
    }
  );

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [abilities, setAbilities] = useState([]);

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

  useEffect(() => {
    if (isLoading) return;

    setTitle(reportData.title);
    setDescription(reportData.description);
    setAbilities(reportData?.abilities);
  }, [isLoading]);

  const onSubmitReport = async (event) => {
    event.preventDefault();

    const data = {
      title,
      description,
      startDate: reportData.startDate,
      endDate: reportData.endDate,
      reportAbility: abilities.map(({ id, weight }) => ({ abilityId: id, weight })),
    };

    onEditReport.mutate(data);
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 수정을 취소하시겠습니까?')) {
      history.push(`/${username}/reports`);
    }
  };

  if (isLoading) return <></>;

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        <h2>리포트 수정하기</h2>

        <ReportInfo
          nickname={nickname}
          title={title}
          setTitle={setTitle}
          desc={description}
          setDescription={setDescription}
          startDate={reportData.startDate}
          endDate={reportData.endDate}
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
