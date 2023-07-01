import { createAxiosInstance } from '../utils/axiosInstance';

const instanceWithoutToken = createAxiosInstance();
const instanceWithToken = (accessToken) => createAxiosInstance({ accessToken });

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylog = ({ id, accessToken }) =>
  instanceWithToken(accessToken).get(`/studylogs/${id}`);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylogs = ({ query, accessToken }) => {
  if (query.type === 'searchParams') {
    return instanceWithToken(accessToken).get('/studylogs', {
      params: new URLSearchParams(query.data.toString()),
    });
  }

  if (query.type === 'filter') {
    const searchParams = Object.entries(query?.data?.postQueryParams).map(
      ([key, value]) => `${key}=${value}`
    );
    const filterQuery = query.data.filterQuery.length
      ? query.data.filterQuery.map(
          ({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`
        )
      : '';
    return instanceWithToken(accessToken).get('/studylogs', {
      params: new URLSearchParams([...filterQuery, ...searchParams].join('&')),
    });
  }

  return instanceWithToken(accessToken).get('/studylogs');
};

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestPostStudylog = ({ accessToken, data }) =>
  instanceWithToken(accessToken).post('/studylogs', data);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestEditStudylog = ({ id, data, accessToken }) =>
  instanceWithToken(accessToken).put(`/studylogs/${id}`, data);

export const requestDeleteStudylog = ({ id, accessToken }) =>
  instanceWithToken(accessToken).delete(`/studylogs/${id}`);

export const requestGetUserStudylogs = (username, postSearchParams, filteringOption) => {
  const searchParams = Object.entries(postSearchParams).map(([key, value]) => `${key}=${value}`);
  const filterQuery = filteringOption.length
    ? filteringOption.map(({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`)
    : '';

  return instanceWithoutToken.get(
    `/members/${username}/studylogs?${[...filterQuery, ...searchParams].join('&')}`
  );
};

export const requestGetCalendar = (year, month, username) =>
  instanceWithoutToken.get(`/members/${username}/calendar-studylogs?year=${year}&month=${month}`);

export const requestGetMyScrap = ({ username, accessToken, postQueryParams }) => {
  const searchParams = Object.entries(postQueryParams).map(([key, value]) => `${key}=${value}`);

  return instanceWithToken(accessToken).get(
    `/members/${username}/scrap?${[...searchParams].join('&')}`
  );
};

/* Filter 관련 request */
/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetFilters = () => instanceWithoutToken.get('/filters');

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetMissions = () => instanceWithoutToken.get('/missions');

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetTags = () => instanceWithoutToken.get('/tags');

export const requestGetUserTags = (username) =>
  instanceWithoutToken.get(`/members/${username}/tags`);

/* User 관련 request */
export const loginRequest = ({ code }) => instanceWithoutToken.post('/login/token', { code });

export const getUserProfileRequest = ({ accessToken }) =>
  instanceWithToken(accessToken).get('/members/me');

export const requestGetProfile = (username) =>
  instanceWithoutToken.get(`/members/${username}/profile`);

export const requestEditProfile = (data, accessToken) =>
  instanceWithToken(accessToken).put(`/members/${data.username}`, data);

export const requestGetProfileIntroduction = (username) =>
  instanceWithoutToken.get(`/members/${username}/profile-intro`);

export const requestEditProfileIntroduction = (username, data, accessToken) =>
  instanceWithToken(accessToken).put(`/members/${username}/profile-intro`, data);

/* 사용자 리액션 관련 requests */
export const requestPostScrap = ({ username, accessToken, id: studylogId }) =>
  instanceWithToken(accessToken).post(`/members/${username}/scrap`, {
    studylogId,
  });

export const requestDeleteScrap = ({ username, accessToken, id }) =>
  instanceWithToken(accessToken).delete(`/members/${username}/scrap`, {
    params: {
      studylog: id,
    },
  });

export const requestPostLike = ({ accessToken, id }) =>
  instanceWithToken(accessToken).post(`/studylogs/${id}/likes`);

export const requestDeleteLike = ({ accessToken, id }) =>
  instanceWithToken(accessToken).delete(`/studylogs/${id}/likes`);

export const requestGetMatchedStudylogs = ({ accessToken, startDate, endDate }) =>
  instanceWithToken(accessToken).get('/studylogs/me', {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  });

export const requestImageUpload = (formData) => instanceWithoutToken.post('/images', formData);
