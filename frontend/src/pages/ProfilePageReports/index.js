import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { SelectBox } from '../../components';
import { Container, AddNewReport } from './styles';

const ProfilePageReports = () => {
  const [reports, setReports] = useState([]);
  const { username } = useParams();

  const user = useSelector((state) => state.user.profile);
  const isOwner = !!user.data && username === user.data.username;

  return (
    <Container reportsLength={reports.length}>
      {reports.length === 0 ? (
        <>
          <p>등록된 리포트가 없습니다.</p>
          {isOwner && (
            <>
              <p>리포트를 작성해주세요.</p>
              <AddNewReport to={`/${username}/report/write`}> 새 리포트 등록</AddNewReport>
            </>
          )}
        </>
      ) : (
        <>
          <SelectBox
            // options={missions?.map((mission) => mission.name)}
            // selectedOption={selectedMission}
            // setSelectedOption={setSelectedMission}
            title="리포트 목록입니다,."
            name="reports"
            width="40%"
            maxHeight="10rem"
          />
          {isOwner && <AddNewReport to={`/${username}/report/write`}> 새 리포트 등록</AddNewReport>}
        </>
      )}
    </Container>
  );
};

export default ProfilePageReports;
