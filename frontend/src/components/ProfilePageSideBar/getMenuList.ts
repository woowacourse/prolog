import { PROFILE_PAGE_MENU } from '../../constants';

import { ReactComponent as StudylogIcon } from '../../assets/images/post.svg';
import { ReactComponent as OverviewIcon } from '../../assets/images/overview.svg';
import { ReactComponent as ScrapIcon } from '../../assets/images/scrap.svg';

const getMenuList = ({ username, isOwner }: { username: string; isOwner: boolean }) => {
  const defaultMenu = [
    {
      key: PROFILE_PAGE_MENU.OVERVIEW,
      title: '오버뷰',
      path: `/${username}`,
      Icon: OverviewIcon,
    },
    {
      key: PROFILE_PAGE_MENU.STUDYLOGS,
      title: '학습로그',
      path: `/${username}/studylogs`,
      Icon: StudylogIcon,
    },
  ];
  const privateMenu = [
    {
      key: PROFILE_PAGE_MENU.SCRAPS,
      title: '스크랩',
      path: `/${username}/scraps`,
      Icon: ScrapIcon,
    },
  ];

  return isOwner ? [...defaultMenu, ...privateMenu] : defaultMenu;
};

export default getMenuList;
