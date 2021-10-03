const CONFIRM_MESSAGE = {
  DELETE_POST: '글을 삭제하시겠습니까?',
  DELETE_SCRAP: '스크랩을 취소하시겠습니까?',
};

const ALERT_MESSAGE = {
  FAIL_TO_DELETE_POST: '글을 삭제할 수 없습니다.',
  FAIL_TO_UPLOAD_IMAGE: '이미지 업로드를 할 수 없습니다.',
  NEED_TO_LOGIN: '로그인 후 이용 가능합니다',
};

const ERROR_MESSAGE = {
  DEFAULT: '에러가 발생했습니다. 관리자에게 문의해 주세요',
  LOGIN_DEFAULT: '로그인 정보가 유효하지 않습니다. 다시 로그인해 주세요.',

  1000: '깃허브 로그인에 실패했습니다. 다시 로그인해 주세요.',
  1001: '깃허브 로그인에 실패했습니다. 다시 로그인해 주세요.',
  1002: '로그인 시간이 만료되었습니다. 다시 로그인해 주세요.',

  2001: '글 내용을 작성해 주세요.',
  2002: '글 제목을 입력해 주세요.',
};

const SUCCESS_MESSAGE = {
  CREATE_POST: '글이 작성되었습니다.',
};

const PLACEHOLDER = {
  POST_TITLE: '제목을 입력해주세요',
  POST_CONTENT: '학습로그를 작성해주세요',
  TAG: '#태그선택',
};

const SNACKBAR_MESSAGE = {
  SUCCESS_TO_SCRAP: '스크랩을 완료했습니다.',
  FAIL_TO_SCRAP: '스크랩을 취소했습니다.',
};

export {
  CONFIRM_MESSAGE,
  ALERT_MESSAGE,
  ERROR_MESSAGE,
  SUCCESS_MESSAGE,
  PLACEHOLDER,
  SNACKBAR_MESSAGE,
};
