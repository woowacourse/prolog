/** @jsxImportSource @emotion/react */

import { useEffect } from 'react';
import useStudyLog from '../../hooks/useStudyLog';

import BannerList from '../../components/Banner/BannerList';
import RecentStudyLogList from './RecentStudyLogList';

import LOCAL_STORAGE_KEY from '../../constants/localStorage';

import { MainContentStyle } from '../../PageRouter';

import bannerList from '../../configs/bannerList';

const MainPage = () => {
  const accessToken = localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
  const { response: recentStudylogs, getAllData: fetchRecentStudylogs } = useStudyLog([]);

  useEffect(() => {
    fetchRecentStudylogs(
      { type: 'searchParams', data: 'size=3' },
      accessToken && JSON.parse(accessToken)
    );
  }, []);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={MainContentStyle}>
        <RecentStudyLogList studylogs={recentStudylogs.data} />
      </main>
    </>
  );
};

export default MainPage;
