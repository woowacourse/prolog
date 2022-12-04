import { PATH, PROFILE_PAGE_MENU } from './constants';
import {
  EditStudylogPage,
  LoginCallbackPage,
  MainPage,
  NewStudylogPage,
  StudylogPage,
  ProfilePage,
  ProfilePageEditReport,
  ProfilePageNewReport,
  ProfilePageStudylogs,
  ProfilePageReports,
  ProfilePageScraps,
  StudylogListPage,
} from './pages';
import AbilityPage from './pages/AbilityPage';
import EditLevellogPage from './pages/EditLevellogPage';
import LevellogListPage from './pages/LevellogListPage';
import LevellogPage from './pages/LevellogPage';
import NewLevellogPage from './pages/NewLevellogPage';
import ProfilePageReportsList from './pages/ProfilePageReportsList';
import RoadmapPage from './pages/RoadmapPage';

const pageRoutes = [
  {
    path: ['/roadmap'],
    render: () => <RoadmapPage />,
  },
  {
    path: [PATH.ROOT],
    render: () => <MainPage />,
  },

  {
    path: [PATH.LOGIN_CALLBACK],
    render: () => <LoginCallbackPage />,
  },
  { path: [PATH.STUDYLOG], render: () => <StudylogListPage /> },
  { path: [PATH.NEW_STUDYLOG], render: () => <NewStudylogPage /> },
  {
    path: [`${PATH.STUDYLOG}/:id`],
    render: () => <StudylogPage />,
  },
  {
    path: `${PATH.STUDYLOG}/:id/edit`,
    render: () => <EditStudylogPage />,
  },
  // {
  //   path: [PATH.LEVELLOG],
  //   render: () => <LevellogListPage />,
  // },
  // {
  //   path: [PATH.NEW_LEVELLOG],
  //   render: () => <NewLevellogPage />,
  // },
  // {
  //   path: [`${PATH.LEVELLOG}/:id`],
  //   render: () => <LevellogPage />,
  // },
  // {
  //   path: [`${PATH.LEVELLOG}/:id/edit`],
  //   render: () => <EditLevellogPage />,
  // },
  {
    path: [PATH.PROFILE],
    render: () => <ProfilePage menu={PROFILE_PAGE_MENU.OVERVIEW} />,
  },
  {
    path: [PATH.PROFILE_STUDYLOGS],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.STUDYLOGS}>
        <ProfilePageStudylogs />
      </ProfilePage>
    ),
  },
  {
    path: [PATH.PROFILE_REPORTS],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
        <ProfilePageReportsList />
      </ProfilePage>
    ),
  },
  {
    path: [PATH.PROFILE_NEW_REPORT],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
        <ProfilePageNewReport />
      </ProfilePage>
    ),
  },
  {
    path: [PATH.PROFILE_REPORT],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
        <ProfilePageReports />
      </ProfilePage>
    ),
  },
  {
    path: [`${PATH.PROFILE_REPORTS}/:id/edit`],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
        <ProfilePageEditReport />
      </ProfilePage>
    ),
  },
  {
    path: [PATH.PROFILE_SCRAPS],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.SCRAPS}>
        <ProfilePageScraps />
      </ProfilePage>
    ),
  },
  {
    path: [PATH.ABILITY],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.ABILITY}>
        <AbilityPage />
      </ProfilePage>
    ),
  },
];

export default pageRoutes;
