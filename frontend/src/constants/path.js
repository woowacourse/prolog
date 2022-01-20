const PATH = {
  ROOT: '/',
  PROFILE: '/:username',
  PROFILE_STUDYLOG: '/:username/studylogs',
  PROFILE_SCRAPS: '/:username/scraps',
  PROFILE_ACCOUNT: '/:username/account',
  PROFILE_REPORTS: '/:username/reports',
  PROFILE_REPORT: '/:username/reports/:reportId',
  PROFILE_NEW_REPORT: '/:username/reports/write',
  LOGIN_CALLBACK: '/login/callback',
  NEW_STUDYLOG: '/studylog/write',
  ABILITY: '/:username/ability',
  STUDYLOG: '/studylogs',
};

export default PATH;
