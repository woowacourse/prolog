import { useContext, useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { css } from '@emotion/react';

import { Ability } from '../ProfilePageReports/AbilityGraph';

import useRequest from '../../hooks/useRequest';
import { requestGetReportList } from '../../service/requests';
import { UserContext } from '../../contexts/UserProvider';

import { Chip, Pagination } from '../../components';

import { COLOR, REQUEST_REPORT_TYPE } from '../../constants';

import {
  Container,
  AddNewReportLink,
  ReportList,
  Card,
  AbilityList,
  StudylogCount,
  Badge,
} from './styles';

import { ReactComponent as StudylogIcon } from '../../assets/images/post.svg';

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

const defaultReports = {
  reports: [],
  currPage: 1,
  totalSize: 0,
  totalPage: 1,
};

const ProfilePageReportsList = () => {
  const { username } = useParams<{ username: string }>();

  const [reports, setReports] = useState(defaultReports);

  const { user: loginUser } = useContext(UserContext);
  const { username: loginUsername } = loginUser;

  const isOwner = username === loginUsername;

  // const { response: reports, fetchData: getReports } = useRequest({}, (page = 1) =>
  //   requestGetReportList({
  //     username,
  //     type: REQUEST_REPORT_TYPE.ALL,
  //     size: 4,
  //     page,
  //   })
  // );

  // useEffect(() => {
  //   getReports();
  // }, [username]);

  const getReports = async (page = 1) => {
    try {
      const response = await requestGetReportList({
        username,
        type: REQUEST_REPORT_TYPE.ALL,
        size: 4,
        page,
      });

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const fetchReports = await response.json();
      setReports({ ...fetchReports, currPage: page });
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    getReports();
  }, []);

  const { reports: reportList } = reports;

  const onSetPage = (page: number) => {
    getReports(page);
  };

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
            <AddNewReportLink to={`/${username}/reports/write`}>새 리포트 등록</AddNewReportLink>
          </>
        )}
      </Container>
    );
  }

  return (
    <Container>
      {isOwner && (
        <AddNewReportLink
          to={`/${username}/reports/write`}
          css={css`
            position: absolute;
            bottom: 0.5rem;
            right: 0.5rem;
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
                  {abilityGraph.abilities
                    .filter(({ isPresent }) => isPresent)
                    .map(({ id, name }: Ability) => (
                      <li key={id}>
                        <Chip backgroundColor={`${COLOR.LIGHT_BLUE_100}`}>{name}</Chip>
                      </li>
                    ))}
                </AbilityList>

                <StudylogCount>
                  <StudylogIcon />
                  <p>{studylogs.length}개의 학습로그</p>
                </StudylogCount>

                <time>{new Date(createdAt).toLocaleDateString()}</time>
              </Link>
            </Card>
          )
        )}
      </ReportList>
      <Pagination postsInfo={reports} onSetPage={onSetPage} />
    </Container>
  );
};

export default ProfilePageReportsList;
