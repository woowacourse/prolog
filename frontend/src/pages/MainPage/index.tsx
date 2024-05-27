/** @jsxImportSource @emotion/react */

import React, { useContext, useEffect, useState } from 'react';

import { UserContext } from '../../contexts/UserProvider';
import bannerList from '../../configs/bannerList';
import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';
import PopularStudyLogList from './PopularStudyLogList';

import { MainContentStyle } from '../../PageRouter';
import { getRowGapStyle } from '../../styles/layout.styles';

import { requestGetStudylogs } from '../../service/requests';
import { ERROR_MESSAGE } from '../../constants';

const MainPage = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const [recentStudylogs, setRecentStudylogs] = useState(null);
  const [popularStudyLogs, setPopularStudyLogs] = useState(null);

  useEffect(() => {
    const fetchRecentStudylogs = async () => {
      try {
        const response = await requestGetStudylogs({
          query: { type: 'searchParams', data: 'size=3' },
          accessToken,
        });

        if (!response) {
          return [];
        }

        const { data } = response.data;
        setRecentStudylogs(data);
      } catch (error) {
        alert(ERROR_MESSAGE.DEFAULT);
      }
    };

    fetchRecentStudylogs();
  }, []);

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
