import { useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { NavLink, useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import localStorage from 'local-storage';

import {
  requestDeleteReport,
  requestGetReport,
  requestGetReportList,
} from '../../service/requests';
import { API, REQUEST_REPORT_TYPE } from '../../constants';

import { Button, SelectBox } from '../../components';
import Report from './Report';
import { Container, AddNewReportLink, ButtonWrapper, ReportHeader, ReportBody } from './styles';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';

const ProfilePageReports = () => {
  const history = useHistory();
  const { reportId, username } = useParams();

  const [reportName, setReportName] = useState('');

  const loginUser = useSelector((state) => state.user.profile);
  const isOwner = !!loginUser.data && username === loginUser.data.username;

  const accessToken = localStorage.get(API.ACCESS_TOKEN);

  const { response: report, fetchData: getReport } = useRequest(
    {},
    () => requestGetReport(reportId),
    (data) => {
      setReportName(data.title);
    },
    () => {
      alert('존재하지 않는 리포트입니다.');
      history.push(`/${username}/reports`);
    }
  );

  const { response: reports, fetchData: getReports } = useRequest(
    [],
    () => requestGetReportList(username, REQUEST_REPORT_TYPE.SIMPLE),
    (data) => {
      const reportName = data.find((report) => report.id === Number(reportId)).title;

      setReportName(reportName);
    }
  );

  const { mutate: onDeleteReport } = useMutation(
    () => {
      if (!window.confirm('리포트를 삭제하시겠습니까?')) return;

      return requestDeleteReport(reportId, accessToken);
    },
    () => {
      history.push(`/${username}/reports`);
    },
    () => {
      alert('리포트 삭제에 실패하였습니다.');
    }
  );

  const makeSelectOptions = (options) => {
    return options.map((option) => ({ id: option.id, name: option.title }));
  };

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    if (!reports || reports.length === 0) return;

    const searchTargetReportId = reports.find((report) => report.title === reportName)?.id;

    if (searchTargetReportId) {
      history.push(`/${username}/reports/${searchTargetReportId}`);
    }
  }, [reportName]);

  useEffect(() => {
    getReport();
  }, [reportId]);

  useEffect(() => {
    getReports(username);
  }, [username]);

  return (
    <Container reportsLength={reports.length}>
      <ReportHeader>
        {!!reportName && (
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
        )}
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
    </Container>
  );
};

export default ProfilePageReports;
