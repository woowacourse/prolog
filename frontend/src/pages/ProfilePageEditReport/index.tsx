import { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';
import { Button } from '../../components';
import ReportInfo from '../ProfilePageNewReport/ReportInfo';
import { COLOR, ERROR_MESSAGE } from '../../constants';
import { Form, FormButtonWrapper } from '../ProfilePageNewReport/style';
import AbilityGraph from '../ProfilePageNewReport/AbilityGraph';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import axios, { AxiosError, AxiosResponse } from 'axios';
import { BASE_URL } from '../../configs/environment';
import { Editor } from '@toast-ui/react-editor';
import { ErrorData } from '../../apis/ability';

type reportDataType = {
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  reportAbility: { abilityId: number; weight: number }[];
};

const ProfilePageNewReport = () => {
  const history = useHistory();
  const queryClient = useQueryClient();

  const { id, username } = useParams<{ id: string; username: string }>();
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

  /** 리포트 수정 */
  const onEditReport = useMutation<AxiosResponse<null>, AxiosError<ErrorData>, reportDataType>(
    async (reportData: reportDataType) => {
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
        queryClient.invalidateQueries([`${username}-reports-${id}`]);
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
  const [description, setDescription] = useState<Editor | string>('');
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
    setAbilities(reportData?.abilities);
  }, [isLoading]);

  const onSubmitReport = async (event) => {
    event.preventDefault();

    if (description instanceof Editor) {
      const data = {
        title,
        description: description.getInstance().getMarkdown(),
        startDate: reportData.startDate,
        endDate: reportData.endDate,
        reportAbility: abilities.map(({ id, weight }) => ({ abilityId: id, weight })),
      };
      onEditReport.mutate(data);
    }
  };

  const onCancelWriteReport = () => {
    if (window.confirm('리포트 수정을 취소하시겠습니까?')) {
      history.push(`/${username}/reports`);
    }
  };

  return (
    <>
      {isLoading ? (
        <></>
      ) : (
        <Form onSubmit={onSubmitReport}>
          <h2>리포트 수정하기</h2>

          <ReportInfo
            nickname={nickname}
            title={title}
            setTitle={setTitle}
            desc={reportData.description}
            editorRef={setDescription}
            edit={true}
            startDate={reportData.startDate}
            endDate={reportData.endDate}
            setStartDate={undefined}
            setEndDate={undefined}
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
      )}
    </>
  );
};

export default ProfilePageNewReport;
