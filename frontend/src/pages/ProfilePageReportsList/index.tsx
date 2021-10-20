import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';

import { Ability } from '../ProfilePageReports/AbilityGraph';

import useRequest from '../../hooks/useRequest';
import { requestGetReportList } from '../../service/requests';
import { COLOR, REQUEST_REPORT_TYPE } from '../../constants';

import { Chip } from '../../components';
import { ReactComponent as StudyLogIcon } from '../../assets/images/post.svg';
import {
  Container,
  AddNewReportLink,
  ReportList,
  Card,
  AbilityList,
  StudyLogCount,
  Badge,
} from './styles';
import { css } from '@emotion/react';

type Report = {
  id: number;
  title: string;
  description: string;
  abilityGraph: {
    abilities: Ability[];
  };
  studylogs: [];
  represent: boolean;
  createdAt: string;
};

type UserProfile = {
  data: { username: string };
};

interface UserProfileState {
  user: { profile: UserProfile };
}

const ProfilePageReportsList = () => {
  const { username } = useParams<{ username: string }>();

  const user = useSelector<UserProfileState>((state) => state.user.profile) as UserProfile;

  const isOwner = !!user.data && username === user.data.username;
  const { response: reports, fetchData: getReports } = useRequest([], () =>
    requestGetReportList(username, REQUEST_REPORT_TYPE.ALL)
  );

  useEffect(() => {
    getReports();
  }, [username]);

  const { reports: reportList } = reports;

  if (!reportList?.length) {
    return (
      <Container
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
        {isOwner && (
          <>
            <p>리포트를 작성해주세요.</p>
            <AddNewReportLink to={`/${username}/report/write`}>새 리포트 등록</AddNewReportLink>
          </>
        )}
      </Container>
    );
  }

  return (
    <Container>
      {isOwner && (
        <AddNewReportLink
          to={`/${username}/report/write`}
          css={css`
            position: absolute;
            bottom: -6rem;
            right: 0;
          `}
        >
          새 리포트 등록
        </AddNewReportLink>
      )}

      <ReportList>
        {reportList?.map(
          ({ id, title, description, abilityGraph, studylogs, represent, createdAt }: Report) => (
            <Card key={id}>
              {represent && (
                <Badge>
                  <span>대표 리포트</span>
                </Badge>
              )}

              <Link to={`/${username}/reports/${id}`}>
                <h4>{title}</h4>
                <p>{description}</p>

                <AbilityList>
                  {abilityGraph.abilities.map(({ id, name }: Ability) => (
                    <li key={id}>
                      <Chip backgroundColor={`${COLOR.LIGHT_BLUE_100}`}>{name}</Chip>
                    </li>
                  ))}
                </AbilityList>

                <StudyLogCount>
                  <StudyLogIcon />
                  <p>{studylogs.length}개의 학습로그</p>
                </StudyLogCount>

                <time>{new Date(createdAt).toLocaleDateString()}</time>
              </Link>
            </Card>
          )
        )}
      </ReportList>
    </Container>
  );
};

export default ProfilePageReportsList;
