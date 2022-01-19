import { BannerType } from '../components/Banner/Banner';
import { COLOR } from '../constants';

import prologIcon from '../assets/images/prolog-banner-image.png';
import wootecoIcon from '../assets/images/woteco-logo.png';

// TODO: textContents Template Literal로 변경
const bannerList: BannerType[] = [
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
  {
    backgroundColor: COLOR.WHITE,
    sideImageUrl: wootecoIcon,
    textContents: (
      <>
        <p>자기주도 학습 ∙ 현장중심 경험 ∙ 깊이있는 협업</p>
        <h2>우아한테크코스</h2>
      </>
    ),
    reverse: true,
    showMoreLink: 'https://woowacourse.github.io/',
    showMoreLinkText: '알아보러가기',
    sideImagePadding: 10,
  },
  {
    backgroundColor: COLOR.RED_600,
    textContents: (
      <>
        <h2>(환) 우아한테크코스 4기🥳 (영)</h2>
        <p>4기 크루 드루와 드루와~~</p>
      </>
    ),
    reverse: false,
  },
];

export default bannerList;
