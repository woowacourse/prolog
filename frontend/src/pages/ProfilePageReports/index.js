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

const ProfilePageReports = () => {
  const [reports, setReports] = useState([]);
  const [reportName, setReportName] = useState('');
  const [report, setReport] = useState(null);

  const { username } = useParams();

  const user = useSelector((state) => state.user.profile);
  const isOwner = !!user.data && username === user.data.username;

  const accessToken = localStorage.get(API.ACCESS_TOKEN);

  const getReport = async (reportId) => {
    try {
      const response = await requestGetReport(reportId);

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

      if (reportList.length !== 0) {
        setReportName(reportList[0].title);
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    getReports(username);
  }, [username]);

  useEffect(() => {
    if (!reports || reports.length === 0) return;

    const searchTargetReportId = reports.find((report) => report.title === reportName)?.id;

    if (searchTargetReportId) {
      getReport(searchTargetReportId);
    }
  }, [reportName]);

  const onDeleteReport = async () => {
    if (!window.confirm('리포트를 삭제하시겠습니까?')) return;

    const reportId = reports?.find((report) => report.title === reportName)?.id;

    try {
      const response = await requestDeleteReport(reportId, accessToken);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      getReports(username);
    } catch (error) {
      console.error(error);
      alert('리포트 삭제에 실패하였습니다.');
    }
  };

  const makeSelectOptions = (options) => {
    return options.map((option) => ({ id: option.id, name: option.title }));
  };

  return (
    <Container reportsLength={reports.length}>
      {reports.length === 0 ? (
        <>
          <p>등록된 리포트가 없습니다.</p>
          {isOwner && (
            <>
              <p>리포트를 작성해주세요.</p>
              <AddNewReportLink to={`/${username}/report/write`}> 새 리포트 등록</AddNewReportLink>
            </>
          )}
        </>
      ) : (
        <>
          <ReportHeader>
            <SelectBox
              options={makeSelectOptions(reports)}
              selectedOption={reportName}
              setSelectedOption={setReportName}
              title="리포트 목록입니다."
              name="reports"
              width="50%"
              maxHeight="10rem"
              size="SMALL"
            />
            {isOwner && (
              <AddNewReportLink to={`/${username}/report/write`}>새 리포트 등록</AddNewReportLink>
            )}
          </ReportHeader>

          <ReportBody>
            {isOwner && (
              <ButtonWrapper>
                <NavLink to={`/${username}/reports/${report?.id}/edit`}>수정</NavLink>
                <Button onClick={onDeleteReport} size="X_SMALL">
                  삭제
                </Button>
              </ButtonWrapper>
            )}
            <Report report={report} />
          </ReportBody>
        </>
      )}
    </Container>
  );
};

export default ProfilePageReports;
