import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';

import { requestGetReport, requestGetReportList } from '../../service/requests';
import { SelectBox } from '../../components';
import { Container, AddNewReportLink } from './styles';
import Report from './Report';

// TODO: 대표리포트라는 것을 표현하기
// TODO: 작성자의 경우 리포트 수정, 삭제 버튼 보이게 만들기
// TODO: 페에지 로딩 때 화면 넣기

const ProfilePageReports = () => {
  const [reports, setReports] = useState([0]);
  const [reportName, setReportName] = useState('');
  const [report, setReport] = useState(null);

  const { username } = useParams();

  const user = useSelector((state) => state.user.profile);
  const isOwner = !!user.data && username === user.data.username;

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
      setReportName(reportList.find((report) => report.represent).title);
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
            <AddNewReportLink to={`/${username}/report/write`}> 새 리포트 등록</AddNewReportLink>
          )}
          <Report report={report} />
        </>
      )}
    </Container>
  );
};

export default ProfilePageReports;
