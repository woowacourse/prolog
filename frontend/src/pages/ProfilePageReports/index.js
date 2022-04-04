import { useContext } from 'react';
import { NavLink, useHistory, useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';

import * as Styled from './styles';
import AbilityGraph from './AbilityGraph';
import ReportStudyLogs from './ReportStudyLogs';
import { Button } from '../../components';
import { useMutation, useQuery } from 'react-query';
import axios from 'axios';
import { BASE_URL } from '../../configs/environment';
import { ERROR_MESSAGE } from '../../constants';

const ProfilePageReports = () => {
  const history = useHistory();
  const { reportId, username } = useParams();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  /** 리포트 목록 가져오기 */
  const { data: reportData = [], isLoading } = useQuery(
    [`${username}-reports-${reportId}`],
    async () => {
      const { data } = await axios({
        method: 'get',
        url: `${BASE_URL}/reports/${reportId}`,
      });

      return data;
    }
  );

  /** 리포트 삭제 */
  const onDeleteReport = useMutation(
    async () => {
      await axios({
        method: 'delete',
        url: `${BASE_URL}/reports/${reportId}`,
        headers: {
          Authorization: `Bearer ${user.accessToken}`,
        },
      });
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

  const onDelete = () => {
    if (window.confirm('리포트를 삭제하시겠습니까?')) {
      onDeleteReport.mutate();
    }
  };

  if (isLoading) {
    return <></>;
  }

  return (
    <Styled.Container>
      <Styled.ReportBody>
        <Styled.Section>
          <h3 id="report-title">{reportData.title}</h3>
        </Styled.Section>

        <Styled.Section>
          <span>🎯 기간</span>
          <p>
            {reportData.startDate} ~ {reportData.endDate}
          </p>
        </Styled.Section>

        {reportData.description && (
          <Styled.Section>
            <span>🎯 리포트 설명</span>
            <p>{reportData.description}</p>
          </Styled.Section>
        )}

        <AbilityGraph abilities={reportData.abilities} />

        <ReportStudyLogs studylogs={reportData.studylogs} />

        {!readOnly && (
          <Styled.ButtonWrapper>
            <NavLink to={`/${username}/reports/${reportId}/edit`}>수정</NavLink>
            <Button onClick={onDelete} size="X_SMALL">
              삭제
            </Button>
          </Styled.ButtonWrapper>
        )}
      </Styled.ReportBody>
    </Styled.Container>
  );
};

export default ProfilePageReports;
