import { PATH, PROFILE_PAGE_MENU } from './constants';
import {
  EditStudylogPage,
  LoginCallbackPage,
  MainPage,
  NewStudylogPage,
  StudylogPage,
  ProfilePage,
  ProfilePageStudylogs,
  ProfilePageScraps,
  StudylogListPage,
  RoadmapPage,
  NewEssayAnswerPage,
  EssayAnswerPage,
  EssayAnswerListPage,
  EditEssayAnswerPage,
  NewArticlePage
} from './pages';

const pageRoutes = [
  {
    path: [PATH.ROADMAP],
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
  { path: [PATH.ARTICLE], render: () => <NewArticlePage /> },
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
    path: [PATH.PROFILE_SCRAPS],
    render: () => (
      <ProfilePage menu={PROFILE_PAGE_MENU.SCRAPS}>
        <ProfilePageScraps />
      </ProfilePage>
    ),
  },
  {
    path: [PATH.NEW_ESSAY_ANSWER],
    render: () => <NewEssayAnswerPage />,
  },
  {
    path: [PATH.ESSAY_ANSWER],
    render: () => <EssayAnswerPage />,
  },
  {
    path: [PATH.ESSAY_ANSWER_LIST],
    render: () => <EssayAnswerListPage />,
  },
  {
    path: '/essay-answers/:id/edit',
    render: () => <EditEssayAnswerPage />,
  }
];

export default pageRoutes;
