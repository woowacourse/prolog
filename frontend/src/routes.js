import { PATH, PROFILE_PAGE_MENU } from './constants';
import {
  EditEssayAnswerPage,
  EditStudylogPage,
  EssayAnswerListPage,
  EssayAnswerPage,
  LoginCallbackPage,
  MainPage,
  NewEssayAnswerPage,
  NewStudylogPage,
  ProfilePage,
  ProfilePageScraps,
  ProfilePageStudylogs,
  QuizAnswerListPage,
  RoadmapPage,
  NewArticlePage,
  ArticleListPage,
  StudylogListPage,
  StudylogPage,
} from './pages';

const pageRoutes = [
  {
    path: '/essay-answers',
    render: () => <EssayAnswerListPage />,
  },
  {
    path: [PATH.ROADMAP],
    render: () => <RoadmapPage />,
  },
  {
    path: [PATH.ROOT],
    // render: () => <MainPage />,
    render: () => <StudylogListPage />,
  },
  {
    path: [PATH.ARTICLE],
    render: () => <ArticleListPage />,
  },
  {
    path: [PATH.LOGIN_CALLBACK],
    render: () => <LoginCallbackPage />,
  },
  {
    path: [PATH.STUDYLOG],
    render: () => <StudylogListPage />,
  },
  {
    path: [PATH.NEW_STUDYLOG],
    render: () => <NewStudylogPage />,
  },
  {
    path: [`${PATH.STUDYLOG}/:id`],
    render: () => <StudylogPage />,
  },
  {
    path: `${PATH.STUDYLOG}/:id/edit`,
    render: () => <EditStudylogPage />,
  },
  { path: [PATH.NEW_ARTICLE], render: () => <NewArticlePage /> },
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
    render: () => <QuizAnswerListPage />,
  },
  {
    path: '/essay-answers/:id/edit',
    render: () => <EditEssayAnswerPage />,
  },
];

export default pageRoutes;
