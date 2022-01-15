import BannerList from '../../components/Banner/BannerList';
import bannerList from './bannerList';

const MainPage = () => {
  return (
    <>
      <BannerList bannerList={bannerList} />
    </>
  );
};

export default MainPage;
