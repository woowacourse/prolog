import { PROFILE_PAGE_MENU } from '../../constants';

import { ReactComponent as StudylogIcon } from '../../assets/images/post.svg';
import { ReactComponent as OverviewIcon } from '../../assets/images/overview.svg';
import { ReactComponent as ScrapIcon } from '../../assets/images/scrap.svg';
import { ReactComponent as ReportIcon } from '../../assets/images/reportIcon.svg';

const getMenuList = ({ username, isOwner }: { username: string; isOwner: boolean }) => {
  const defaultMenu = [
    {
      key: PROFILE_PAGE_MENU.OVERVIEW,
      title: '오버뷰',
      path: `/${username}`,
      Icon: OverviewIcon,
    },
    {
      key: PROFILE_PAGE_MENU.STUDYLOG,
      title: '학습로그',
      path: `/${username}/studylogs`,
      Icon: StudylogIcon,
    },
    {
      key: PROFILE_PAGE_MENU.REPORTS,
      title: '리포트',
      path: `/${username}/reports`,
      Icon: ReportIcon,
    },
  ];
  const privateMenu = [
    {
      key: PROFILE_PAGE_MENU.SCRAPS,
      title: '스크랩',
      path: `/${username}/scraps`,
      Icon: ScrapIcon,
    },
    {
      key: PROFILE_PAGE_MENU.ABILITY,
      title: '역량',
      path: `/${username}/ability`,
      Icon: StudylogIcon,
    },
  ];

  return isOwner ? [...defaultMenu, ...privateMenu] : defaultMenu;
};

export default getMenuList;
