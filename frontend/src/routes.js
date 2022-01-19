import { PATH, PROFILE_PAGE_MENU } from './constants';
import {
  EditPostPage,
  LoginCallbackPage,
  MainPage,
  NewPostPage,
  PostPage,
  ProfilePage,
  ProfilePageEditReport,
  ProfilePageNewReport,
  ProfilePagePosts,
  ProfilePageReports,
  ProfilePageScraps,
  StudyLogListPage,
} from './pages';
import AbilityPage from './pages/AbilityPage';
import ProfilePageReportsList from './pages/ProfilePageReportsList';

const pageRoutes = [
  {
    path: [PATH.ROOT],
    render: () => <MainPage />,
  },

  {
    path: [PATH.LOGIN_CALLBACK],
    render: () => <LoginCallbackPage />,
  },
  { path: [PATH.STUDYLOG], render: () => <StudyLogListPage /> },
  { path: [PATH.NEW_POST], render: () => <NewPostPage /> },
  {
    path: [`${PATH.POST}/:id`],
    render: () => <PostPage />,
  },
  {
    path: `${PATH.POST}/:id/edit`,
    render: () => <EditPostPage />,
  },
  {
    path: [PATH.PROFILE],
    render: () => <ProfilePage menu={PROFILE_PAGE_MENU.OVERVIEW} />,
  },
  {
    path: [PATH.PROFILE_POSTS],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.POSTS}>
        <ProfilePagePosts />
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
