import { useEffect, useState } from 'react';
import { NavLink, useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import localStorage from 'local-storage';

import {
  requestDeleteReport,
  requestGetReport,
  requestGetReportList,
} from '../../service/requests';
import { API } from '../../constants';

import { Button, SelectBox } from '../../components';
import Report from './Report';
import { Container, AddNewReportLink, ButtonWrapper, ReportHeader, ReportBody } from './styles';

// TODO: 대표리포트라는 것을 표현하기
// TODO: 메뉴를 통해 들어왔을 때는 대표 리포트를 가장 먼저 보여주기
// TODO: 페에지 로딩 때 화면 넣기

const ProfilePageReports = () => {
  const [reports, setReports] = useState([0]);
  const [reportName, setReportName] = useState('');
  const [report, setReport] = useState(null);

  const { username } = useParams();

  const user = useSelector((state) => state.user.profile);
  const isOwner = !!user.data && username === user.data.username;

  const accessToken = localStorage.get(API.ACCESS_TOKEN);

  const getReport = async (username, reportId) => {
    try {
      const response = await requestGetReport(username, reportId);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const report = await response.json();
      setReport(report);
    } catch (error) {
      console.error(error);
    }
  };

  const getReports = async (username) => {
    try {
      const response = await requestGetReportList(username);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const reportList = await response.json();
      setReports(reportList);

      setReportName(reportList[0].title);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    getReports(username);
  }, [username]);

  useEffect(() => {
    const searchTargetReportId = reports?.find((report) => report.title === reportName)?.id;

    if (searchTargetReportId) {
      getReport(searchTargetReportId);
    }
  }, [reportName]);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const onDeleteReport = async () => {
    const reportId = reports?.find((report) => report.title === reportName)?.id;

    try {
      const response = await requestDeleteReport(reportId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      alert('리포트를 삭제하였습니다.');
      getReports(username);
    } catch (error) {
      console.error(error);
      alert('리포트 삭제에 실패하였습니다.');
    }
  };

  return (
    <Container reportsLength={reports.length}>
      {reports.length === 0 ? (
        <Container>
          <p>등록된 리포트가 없습니다.</p>
          {isOwner && (
            <>
              <p>리포트를 작성해주세요.</p>
              <AddNewReportLink to={`/${username}/report/write`}> 새 리포트 등록</AddNewReportLink>
            </>
          )}
        </Container>
      ) : (
        <>
          <ReportHeader>
            <SelectBox
              options={reports?.map((report) => report.title)}
              selectedOption={reportName}
              setSelectedOption={setReportName}
              title="리포트 목록입니다,."
              name="reports"
              width="40%"
              maxHeight="10rem"
              size="SMALL"
            />
            {isOwner && (
              <AddNewReportLink to={`/${username}/report/write`}>새 리포트 등록</AddNewReportLink>
            )}
          </ReportHeader>

          <ReportBody>
            <ButtonWrapper>
              <NavLink to={`/${username}/report/write`}>수정</NavLink>
              <Button onClick={onDeleteReport} size="X_SMALL">
                삭제
              </Button>
            </ButtonWrapper>
            <Report report={report} />
          </ReportBody>
        </>
      )}
    </Container>
  );
};

export default ProfilePageReports;
