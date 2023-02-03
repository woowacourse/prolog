const PATH = {
  ROOT: '/',
  PROFILE: '/:username',
  PROFILE_STUDYLOGS: '/:username/studylogs',
  PROFILE_SCRAPS: '/:username/scraps',
  PROFILE_ACCOUNT: '/:username/account',
  PROFILE_REPORTS: '/:username/reports',
  PROFILE_REPORT: '/:username/reports/:reportId',
  PROFILE_NEW_REPORT: '/:username/reports/write',
  LOGIN_CALLBACK: '/login/callback',
  STUDYLOG: '/studylogs',
  NEW_STUDYLOG: '/studylog/write',
  LEVELLOG: '/levellogs',
  NEW_LEVELLOG: '/levellog/write',
  ABILITY: '/:username/ability',
  ROADMAP: '/roadmap',
  NEW_ESSAY_ANSWER: '/quizzes/:quizId/essay-answers/form',
  ESSAY_ANSWER: '/essay-answers/:essayAnswerId',
  ESSAY_ANSWER_LIST: '/quizzes/:quizId/essay-answers',
};

export default PATH;
