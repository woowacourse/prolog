/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';
import useStudylog from '../../hooks/useStudylog';

import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';
import ERROR_CODE from '../../constants/errorCode';

import { MainContentStyle } from '../../PageRouter';

import bannerList from '../../configs/bannerList';
import { UserContext } from '../../contexts/UserProvider';

const MainPage = () => {
  const { user, onLogout } = useContext(UserContext);
  const { accessToken } = user;
  const { response: recentStudylogs, errorObj: recentStudylogsError, getAllData: fetchRecentStudylogs } = useStudylog([]);

  useEffect(() => {
    fetchRecentStudylogs({ query: { type: 'searchParams', data: 'size=3' }, accessToken });
  }, [accessToken]);

  // 임시 방편으로 EXPIRED_ACCESS_TOKEN에러 시 토큰 초기화 시행
  // TODO: Unauthorize Error 공통 대응 필요.
  useEffect(() => {
    if (recentStudylogsError.code === ERROR_CODE.EXPIRED_ACCESS_TOKEN) {
      // 토큰 초기화
      onLogout();
    }
  }, [recentStudylogsError])


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
