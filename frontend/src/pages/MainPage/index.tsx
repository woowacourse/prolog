/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';

import { UserContext } from '../../contexts/UserProvider';
import bannerList from '../../configs/bannerList';
import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';
import PopularStudyLogList from './PopularStudyLogList';

import { MainContentStyle } from '../../PageRouter';
import { getRowGapStyle } from '../../styles/layout.styles';

import {
  useGetPopularStudylogsQuery,
  useGetRecentStudylogsQuery,
} from '../../hooks/queries/studylog';

const MainPage = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const { data: recentStudylogs, refetch: refetchRecentStudylogs } = useGetRecentStudylogsQuery();

  const {
    data: popularStudyLogs,
    refetch: refetchPopularStudyLogs,
  } = useGetPopularStudylogsQuery();

  useEffect(() => {
    refetchRecentStudylogs();
    refetchPopularStudyLogs();
  }, [accessToken]);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={[MainContentStyle, getRowGapStyle(5.8)]}>
        {popularStudyLogs && <PopularStudyLogList studylogs={popularStudyLogs} />}
        {recentStudylogs && <RecentStudylogList studylogs={recentStudylogs} />}
      </main>
    </>
  );
};

export default MainPage;
