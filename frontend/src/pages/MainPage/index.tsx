/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';
import useStudylog from '../../hooks/useStudylog';

import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';

import { MainContentStyle } from '../../PageRouter';

import bannerList from '../../configs/bannerList';
import { UserContext } from '../../contexts/UserProvider';
import PopularStudyLogList from './PopularStudyLogList';
import useFetchData from '../../hooks/useFetchData';
import { getPopularStudylogs } from '../../apis/studylogs';

import type { Studylog } from '../../models/Studylogs';

const MainPage = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;
  const { response: recentStudylogs, getAllData: fetchRecentStudylogs } = useStudylog([]);
  const fetchPopularStudylogsRequest = useFetchData<{
    data: Studylog[];
  }>({ data: [] }, () => getPopularStudylogs({ accessToken }));

  useEffect(() => {
    fetchRecentStudylogs({ query: { type: 'searchParams', data: 'size=3' }, accessToken });
    fetchPopularStudylogsRequest.refetch();
  }, [accessToken]);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={MainContentStyle}>
        {fetchPopularStudylogsRequest.isLoading &&
          fetchPopularStudylogsRequest.response.data.length === 0 && <p>로딩중</p>}
        {fetchPopularStudylogsRequest.response.data.length !== 0 && (
          <PopularStudyLogList studylogs={fetchPopularStudylogsRequest.response.data} />
        )}
        {recentStudylogs.data && <RecentStudylogList studylogs={recentStudylogs.data} />}
      </main>
    </>
  );
};

export default MainPage;
