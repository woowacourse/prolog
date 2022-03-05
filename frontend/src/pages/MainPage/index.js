/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';
import useStudylog from '../../hooks/useStudylog';

import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';

import { MainContentStyle } from '../../PageRouter';

import bannerList from '../../configs/bannerList';
import { UserContext } from '../../contexts/UserProvider';

const MainPage = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;
  const { response: recentStudylogs, getAllData: fetchRecentStudylogs } = useStudylog([]);

  useEffect(() => {
    fetchRecentStudylogs({ query: { type: 'searchParams', data: 'size=3' }, accessToken });
  }, [accessToken]);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={MainContentStyle}>
        <RecentStudylogList studylogs={recentStudylogs.data} />
      </main>
    </>
  );
};

export default MainPage;
