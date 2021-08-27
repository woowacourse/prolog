import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';

const ProfilePageNewReport = () => {
  const { username } = useParams();

  const history = useHistory();

  const user = useSelector((state) => state.user.profile);

  const isLoggedIn = !!user.data;

  useEffect(() => {
    if (isLoggedIn) {
      if (username !== user.data.username) {
        alert('본인의 리포트만 작성할 수 있습니다.');
        history.push(`/${username}/reports`);
      }
    }
  }, [isLoggedIn]);

  return (
    <article style={{ width: '100%' }}>
      <section>report subject & desc</section>
      <section>abilities</section>
      <section>학습로그</section>
    </article>
  );
};

export default ProfilePageNewReport;
