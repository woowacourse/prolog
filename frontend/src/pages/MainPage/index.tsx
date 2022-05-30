/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';

import { UserContext } from '../../contexts/UserProvider';
import useFetchData from '../../hooks/useFetchData';
import bannerList from '../../configs/bannerList';
import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';
import PopularStudyLogList from './PopularStudyLogList';
import { requestGetStudylogs } from '../../service/requests';

import { MainContentStyle } from '../../PageRouter';
import { getRowGapStyle } from '../../styles/layout.styles';

import type { Studylog } from '../../models/Studylogs';
import { useQuery } from 'react-query';
import axios from 'axios';
import { BASE_URL } from '../../configs/environment';

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

  // @TODO: 로딩 및 에러 처리
  const { isLoading, error, data: popularStudyLogs, refetch: refetchPopularStudyLogs } = useQuery<
    Studylog[]
  >('popularStudyLogs', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/studylogs/popular`,
      headers: { Authorization: 'Bearer ' + accessToken },
    });

    return data?.data;
  });

  useEffect(() => {
    fetchRecentStudylogsRequest.refetch();
    refetchPopularStudyLogs();
  }, [accessToken]);

  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={[MainContentStyle, getRowGapStyle(5.8)]}>
        {/* TODO: 로딩상태 관리 */}
        {popularStudyLogs && <PopularStudyLogList studylogs={popularStudyLogs} />}
        {fetchRecentStudylogsRequest.response.data.length !== 0 && (
          <RecentStudylogList studylogs={fetchRecentStudylogsRequest.response.data} />
        )}
      </main>
    </>
  );
};

export default MainPage;
