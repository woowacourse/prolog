import { useContext } from 'react';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';

import { UserContext } from '../../contexts/UserProvider';
import * as Styled from './styles';
import { useQuery } from 'react-query';
import axios from 'axios';
import { BASE_URL } from '../../configs/environment';

// type Report = {
//   id: number;
//   title: string;
//   description: string;
//   startDate: string;
//   endDate: string;
// };

const ProfilePageReportsList = () => {
  const { username } = useParams<{ username: string }>();
  const { user } = useContext(UserContext);
  const readOnly = username !== user.username;

  /** 리포트 목록 가져오기 */
  const { data: ReportList = [] } = useQuery([`${username}-studylogs`], async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/members/${username}/reports`,
    });

    return { ...data };
  });

  if (!ReportList?.length) {
    return (
      <Styled.Container
        css={css`
          height: 70vh;

          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;

          p {
            margin: 0;
            font-size: 2rem;
            line-height: 1.5;
          }
        `}
      >
        <p>등록된 리포트가 없습니다.</p>
        {!readOnly && (
          <>
            <p>리포트를 작성해주세요.</p>
            <Styled.AddFirstReportLink to={`/${username}/reports/write`}>
              새 리포트 등록
            </Styled.AddFirstReportLink>
          </>
        )}
      </Styled.Container>
    );
  }

  return (
    <Styled.Container>
      <h2>{user.nickname} 크루의 리포트</h2>

      <Styled.TimelineWrapper>
        {!readOnly && (
          <Styled.AddNewReportLink to={`/${username}/reports/write`}>
            <span>+</span>
          </Styled.AddNewReportLink>
        )}

        <ul>
          {ReportList.map((report) => (
            <Styled.Report key={report.id} readOnly={readOnly}>
              <Styled.ReportDate>
                {report.startDate} ~ {report.endDate}
              </Styled.ReportDate>
              <Styled.ReportTtile>{report.title}</Styled.ReportTtile>
              <Styled.ReportDesc>{report.description}</Styled.ReportDesc>
              <Styled.GoReportLink to={`/${username}/reports/${report.id}`}>
                리포트 보러가기 {'>'}
              </Styled.GoReportLink>
            </Styled.Report>
          ))}
        </ul>
      </Styled.TimelineWrapper>
    </Styled.Container>
  );
};

export default ProfilePageReportsList;
