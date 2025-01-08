import { createAxiosInstance } from '../utils/axiosInstance';

const customAxios = (accessToken?: string) => createAxiosInstance({ accessToken });

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylog = ({ id, accessToken }) =>
  customAxios(accessToken).get(`/studylogs/${id}`);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylogs = ({ query, accessToken }) => {
  if (query.type === 'searchParams') {
    return customAxios(accessToken).get('/studylogs', {
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
    return customAxios(accessToken).get('/studylogs', {
      params: new URLSearchParams([...filterQuery, ...searchParams].join('&')),
    });
  }

  return customAxios(accessToken).get('/studylogs');
};

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestPostStudylog = ({ accessToken, data }) =>
  customAxios(accessToken).post('/studylogs', data);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestEditStudylog = ({ id, data, accessToken }) =>
  customAxios(accessToken).put(`/studylogs/${id}`, data);

export const requestDeleteStudylog = ({ id, accessToken }) =>
  customAxios(accessToken).delete(`/studylogs/${id}`);

export const requestGetUserStudylogs = (username, postSearchParams, filteringOption) => {
  const searchParams = Object.entries(postSearchParams).map(([key, value]) => `${key}=${value}`);
  const filterQuery = filteringOption.length
    ? filteringOption.map(({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`)
    : '';

  return customAxios().get(
    `/members/${username}/studylogs?${[...filterQuery, ...searchParams].join('&')}`
  );
};

export const requestGetCalendar = (year, month, username) =>
  customAxios().get(`/members/${username}/calendar-studylogs?year=${year}&month=${month}`);

export const requestGetMyScrap = ({ username, accessToken, postQueryParams }) => {
  const searchParams = Object.entries(postQueryParams).map(([key, value]) => `${key}=${value}`);

  return customAxios(accessToken).get(`/members/${username}/scrap?${[...searchParams].join('&')}`);
};

/* Filter 관련 request */
/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetFilters = () => customAxios().get('/filters');

export const requestGetFiltersWithAccessToken = (accessToken) =>
  customAxios(accessToken).get('/filters');

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetMissions = () => customAxios().get('/missions');

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetTags = () => customAxios().get('/tags');

export const requestGetUserTags = (username) => customAxios().get(`/members/${username}/tags`);

/* User 관련 request */
export const loginRequest = ({ code }) => customAxios().post('/login/token', { code });

export const getUserProfileRequest = ({ accessToken }) =>
  customAxios(accessToken).get('/members/me');

export const requestGetProfile = (username) => customAxios().get(`/members/${username}/profile`);

export const requestEditProfile = (data, accessToken) =>
  customAxios(accessToken).put(`/members/${data.username}`, data);

export const requestGetProfileIntroduction = (username) =>
  customAxios().get(`/members/${username}/profile-intro`);

export const requestEditProfileIntroduction = (username, data, accessToken) =>
  customAxios(accessToken).put(`/members/${username}/profile-intro`, data);

/* 사용자 리액션 관련 requests */
export const requestPostScrap = ({ username, accessToken, id: studylogId }) =>
  customAxios(accessToken).post(`/members/${username}/scrap`, {
    studylogId,
  });

export const requestDeleteScrap = ({ username, accessToken, id }) =>
  customAxios(accessToken).delete(`/members/${username}/scrap`, {
    params: {
      studylog: id,
    },
  });

export const requestPostLike = ({ accessToken, id }) =>
  customAxios(accessToken).post(`/studylogs/${id}/likes`);

export const requestDeleteLike = ({ accessToken, id }) =>
  customAxios(accessToken).delete(`/studylogs/${id}/likes`);

export const requestGetMatchedStudylogs = ({ accessToken, startDate, endDate }) =>
  customAxios(accessToken).get('/studylogs/me', {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  });

export const requestImageUpload = (formData) => customAxios().post('/images', formData);
