/** @jsxImportSource @emotion/react */

import bannerList from '../../configs/bannerList';
import BannerList from '../../components/Banner/BannerList';
import RecentStudylogList from './RecentStudylogList';
import PopularStudyLogList from './PopularStudyLogList';

import { MainContentStyle } from '../../PageRouter';
import { getRowGapStyle } from '../../styles/layout.styles';

const MainPage = () => {
  return (
    <>
      <BannerList bannerList={bannerList} />
      <main css={[MainContentStyle, getRowGapStyle(5.8)]}>
        {/* TODO: 로딩상태 관리 */}
        <PopularStudyLogList />
        <RecentStudylogList />
      </main>
    </>
  );
};

export default MainPage;
