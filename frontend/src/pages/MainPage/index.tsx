/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';

import { UserContext } from '../../contexts/UserProvider';
import useFetchData from '../../hooks/useFetchData';
import bannerList from '../../configs/bannerList';
import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';
import PopularStudyLogList from './PopularStudyLogList';
import { requestGetPopularStudylogs } from '../../apis/studylogs';
import { requestGetStudylogs } from '../../service/requests';

import { MainContentStyle } from '../../PageRouter';

import type { Studylog } from '../../models/Studylogs';

const MainPage = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const fetchRecentStudylogsRequest = useFetchData<{
    data: Studylog[];
  }>(
    { data: [] },
    () => requestGetStudylogs({ query: { type: 'searchParams', data: 'size=3' }, accessToken }),
    { initialFetch: false }
  );

  const fetchPopularStudylogsRequest = useFetchData<{
    data: Studylog[];
  }>({ data: [] }, () => requestGetPopularStudylogs({ accessToken }), { initialFetch: false });

  useEffect(() => {
    fetchRecentStudylogsRequest.refetch();
    fetchPopularStudylogsRequest.refetch();
  }, [accessToken]);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={MainContentStyle}>
        {/* TODO: 로딩상태 관리 */}
        {fetchPopularStudylogsRequest.response.data.length !== 0 && (
          <PopularStudyLogList studylogs={fetchPopularStudylogsRequest.response.data} />
        )}
        {fetchRecentStudylogsRequest.response.data.length !== 0 && (
          <RecentStudylogList studylogs={fetchRecentStudylogsRequest.response.data} />
        )}
      </main>
    </>
  );
};

export default MainPage;
