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
import { useGetRecentLevellogs } from '../../hooks/queries/levellog';
import RecentLevellogList from './RecentLevellogList';
import { FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';

const FETCH_SIZE = 3;

const MainPage = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const { data: recentStudylogs, refetch: refetchRecentStudylogs } = useGetRecentStudylogsQuery();

  const {
    data: popularStudyLogs,
    refetch: refetchPopularStudyLogs,
  } = useGetPopularStudylogsQuery();
    
   const { data: recentLevellogs, refetch: refetchRecentLevellogs } = useGetRecentLevellogs(FETCH_SIZE);


  useEffect(() => {
    refetchRecentStudylogs();
    refetchPopularStudyLogs();
    refetchRecentLevellogs();
  }, [accessToken]);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={[MainContentStyle, FlexStyle, FlexColumnStyle, getRowGapStyle(3)]}>
        {popularStudyLogs && <PopularStudyLogList studylogs={popularStudyLogs} />}
        {recentStudylogs && <RecentStudylogList studylogs={recentStudylogs} />}
        {recentLevellogs && <RecentLevellogList levellogs={recentLevellogs} />}
      </main>
    </>
  );
};

export default MainPage;
