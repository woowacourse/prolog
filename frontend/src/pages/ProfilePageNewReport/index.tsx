import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { useMutation } from 'react-query';
import axios, { AxiosError, AxiosResponse } from 'axios';

import { UserContext } from '../../contexts/UserProvider';
import { Button } from '../../components';
import ReportInfo from './ReportInfo';
import { COLOR, ERROR_MESSAGE } from '../../constants';
import { Form, FormButtonWrapper } from './style';
import { BASE_URL } from '../../configs/environment';
import useAbility from '../../hooks/Ability/useAbility';
import AbilityGraph from './AbilityGraph';
<<<<<<< HEAD:frontend/src/pages/ProfilePageNewReport/index.js
import ReportStudyLogs from '../../components/ReportStudyLogs/ReportStudyLogs';
import { useGetMatchedStudylogs } from '../../hooks/queries/report';
=======
import useParentAbilityForm from '../../hooks/Ability/useParentAbilityForm';
import { Editor } from '@toast-ui/react-editor';

type reportDataType = {
  title: string;
  description: string | Editor;
  startDate: string;
  endDate: string;
  reportAbility: { abilityId: number; weight: number }[];
};
>>>>>>> 723f1b08 (refactor: ProfilePageNew Report typescript 마이그레이션):frontend/src/pages/ProfilePageNewReport/index.tsx

const ProfilePageNewReport = () => {
  const history = useHistory();

  const { username } = useParams<{ username: string }>();
  const { user } = useContext(UserContext);
  const { isLoggedIn, accessToken } = user;
  const nickname = user.nickname ?? user.username;
  const { setAddFormStatus, addFormClose } = useParentAbilityForm();

  const { abilities: abilityList, isLoading } = useAbility({
    username,
    setAddFormStatus,
    addFormClose,
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

  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState<Editor | string>('');
  const [abilities, setAbilities] = useState([]);

  const { data: studylogsData, refetch: getMatchedStudylogs } = useGetMatchedStudylogs({
    accessToken,
    startDate,
    endDate,
  });

  useEffect(() => {
    if (startDate && endDate) {
      getMatchedStudylogs();
    }
  }, [startDate, endDate]);

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
  const onAddReport = useMutation<AxiosResponse<never>, AxiosError<Error>, reportDataType>(
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

    if (description instanceof Editor) {
      /** 리포트 등록 */
      onAddReport.mutate({
        title,
        description: description.getInstance().getMarkdown(),
        startDate,
        endDate,
        reportAbility: abilities.map(({ id, weight }) => ({ abilityId: id, weight })),
      });
    }
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 작성을 취소하시겠습니까?')) {
      history.push(`/${username}/reports`);
    }
  };

  const studylogsMappingData = studylogsData?.map(({ studylog, abilities }) => {
    return { studylog, studylogAbilities: abilities };
  });

  return (
    <>
      <Form onSubmit={onSubmitReport}>
        <h2>새 리포트 작성하기</h2>

        <ReportInfo
          nickname={nickname}
          title={title}
          setTitle={setTitle}
          desc={description!}
          editorRef={setDescription}
          setStartDate={setStartDate}
          setEndDate={setEndDate}
          startDate={undefined}
          endDate={undefined}
          edit={undefined}
        />
        <ReportStudyLogs studylogs={studylogsMappingData} />
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
