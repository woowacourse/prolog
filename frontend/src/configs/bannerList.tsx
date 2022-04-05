import { BannerType } from '../components/Banner/Banner';
import { COLOR } from '../constants';

import prologIcon from '../assets/images/prolog-banner-image.png';
import reportIcon from '../assets/images/report.png';
import rssIcon from '../assets/images/rss.png';

// TODO: textContents Template Literal로 변경
const bannerList: BannerType[] = [
  {
    backgroundColor: COLOR.RED_200,
    sideImageUrl: reportIcon,
    textContents: (
        <>
          <p>셀프체크시트는 이제 그만!</p>
          <h2>리포트 기능 오픈 </h2>
        </>
    ),
    reverse: true,
    showMoreLink: 'https://dev.prolog.techcourse.co.kr/studylogs/2171',
    showMoreLinkText: '자세히 알아보기',
    sideImagePadding: 10,
  },  {
    backgroundColor: COLOR.LIGHT_BLUE_300,
    textContents: (
        <>
          <p>인기있는 학습로그에 도전!</p>
          <h2>인기있는 학습로그 오픈 🎉</h2>
        </>
    ),
    showMoreLink: 'https://dev.prolog.techcourse.co.kr/studylogs/2171',
    showMoreLinkText: '자세히 알아보기',
    sideImagePadding: 10,
  },
  {
    backgroundColor: COLOR.LIGHT_GRAY_50,
    sideImageUrl: rssIcon,
    textContents: (
      <>
        <p>학습로그도 슬랙으로 확인하세요~~</p>
        <h2>RSS 기능 오픈 🎉</h2>
      </>
    ),
    reverse: false,
    showMoreLink: 'https://dev.prolog.techcourse.co.kr/studylogs/2171',
    showMoreLinkText: '자세히 알아보기',
    sideImagePadding: 10,
  },
  {
    backgroundColor: COLOR.DARK_BLUE_800,
    sideImageUrl: prologIcon,
    textContents: (
        <>
          <br />
          <p>내가 안다고 생각한게 진짜 아는걸까?🧐</p>
          <p>
            <strong>학습로그</strong>를 작성하며 메타인지를 경험해보세요!
          </p>
          <h2>PROLOG</h2>
        </>
    ),
    reverse: false,
    sideImagePadding: 0,
  },
];

export default bannerList;
