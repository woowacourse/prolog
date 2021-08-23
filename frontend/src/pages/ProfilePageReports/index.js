import { useState } from 'react';
import { Container } from './styles';
import { Button } from '../../components';

const ProfilePageReports = () => {
  const [reports, setReports] = useState([]);

  return reports.length === 0 ? (
    <Container reportsLength={reports.length}>
      <p>등록된 리포트가 없습니다.</p>
      <p>리포트를 작성해주세요.</p>
      <Button size="X_SMALL" alt="새 리포트 등록하기 버튼입니다.">
        새 리포트 등록
      </Button>
    </Container>
  ) : (
    <Container reportsLength={reports.length}>준비중입니다.</Container>
  );
};

export default ProfilePageReports;
