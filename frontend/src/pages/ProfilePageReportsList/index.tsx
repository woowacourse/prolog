import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';

import useRequest from '../../hooks/useRequest';
import { requestGetReportList } from '../../service/requests';
import { AddNewReportLink } from './styles';

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
    requestGetReportList(username)
  );

  useEffect(() => {
    getReports();
  }, [username]);

  if (!reports.length) {
    return (
      <div style={{ display: 'flex', flexDirection: 'column' }}>
        <p>등록된 리포트가 없습니다.</p>
        {isOwner && (
          <>
            <p>리포트를 작성해주세요.</p>
            <AddNewReportLink to={`/${username}/report/write`}> 새 리포트 등록</AddNewReportLink>
          </>
        )}
      </div>
    );
  }

  return (
    <ul>
      {reports.map(({ id, title }: { id: number; title: string }) => (
        <li key={id}>
          <Link to={`/${username}/reports/${id}`}>{title}</Link>
        </li>
      ))}
    </ul>
  );
};

export default ProfilePageReportsList;
