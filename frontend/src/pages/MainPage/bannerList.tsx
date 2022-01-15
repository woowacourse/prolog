import { BannerType } from '../../components/Banner/Banner';
import { COLOR } from '../../constants';

import prologIcon from '../../assets/images/prolog-banner-image.png';
import wootecoIcon from '../../assets/images/no-profile-image.png';
import pencilIcon from '../../assets/images/pencil_icon.svg';

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
    sideImagePadding: 0,
  },
  {
    backgroundColor: COLOR.RED_600,
    sideImageUrl: pencilIcon,
    textContents: (
      <>
        <p>
          <strong>임시배너</strong>입니다. 임시배너입니다.
        </p>
        <h2>임!시!배!너</h2>
      </>
    ),
    reverse: false,
    sideImagePadding: 20,
  },
  {
    backgroundColor: '#dabff7',
    textContents: (
      <>
        <p>임시배너입니다. 임시배너입니다.</p>
        <h2>임!시!배!너</h2>
        <p>
          하단에도 <strong>작성</strong>할 수 있습니다.
        </p>
        <p>너무 많이는 작성하지 말아주세요</p>
      </>
    ),
    reverse: true,
    sideImagePadding: 20,
    showMoreLink: '/서니',
    showMoreLinkText: '서니 프로필가기 >',
  },
];

export default bannerList;
