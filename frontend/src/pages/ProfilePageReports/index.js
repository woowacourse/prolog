import { useContext } from 'react';
import { NavLink, useHistory, useParams } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';

import * as Styled from './styles';
import AbilityGraph from './AbilityGraph';
import ReportStudyLogs from './ReportStudyLogs';
import { Button } from '../../components';
import { useQuery } from 'react-query';
import axios from 'axios';
import { BASE_URL } from '../../configs/environment';

const ProfilePageReports = () => {
  const { reportId, username } = useParams();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  /** 리포트 목록 가져오기 */
  const { data: reportData = [], isLoading } = useQuery(
    [`${username}-reports-${reportId}`],
    async () => {
      const { data } = await axios({
        method: 'get',
        url: `${BASE_URL}/reports/1`,
      });

      return data;
    }
  );

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
            <Button onClick={() => console.log('delete')} size="X_SMALL">
              삭제
            </Button>
          </Styled.ButtonWrapper>
        )}
      </Styled.ReportBody>
    </Styled.Container>
  );
};

export default ProfilePageReports;
