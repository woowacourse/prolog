import axios from 'axios';
import { BASE_URL } from '../configs/environment';

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylog = ({ id, accessToken }) => {
  const headers = accessToken
    ? {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      }
    : {};

  return axios.get(`${BASE_URL}/studylogs/${id}`, {
    headers: headers,
  });
};

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylogs = ({ query, accessToken }) => {
  const authConfig = accessToken
    ? {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    : {};

  if (query.type === 'searchParams') {
    return axios.get(`${BASE_URL}/studylogs`, {
      ...authConfig,
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

    return axios.get(`${BASE_URL}/studylogs`, {
      ...authConfig,
      params: new URLSearchParams([...filterQuery, ...searchParams].join('&')),
    });
  }

  return axios.get(`${BASE_URL}/studylogs`, authConfig);
};

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestPostStudylog = ({ accessToken, data }) =>
  axios.post(`${BASE_URL}/studylogs`, data, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestEditStudylog = ({ id, data, accessToken }) => {
  return axios.put(`${BASE_URL}/studylogs/${id}`, data, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export const requestDeleteStudylog = ({ id, accessToken }) =>
  axios.delete(`${BASE_URL}/studylogs/${id}`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestGetUserStudylogs = (username, postSearchParams, filteringOption) => {
  const searchParams = Object.entries(postSearchParams).map(([key, value]) => `${key}=${value}`);
  const filterQuery = filteringOption.length
    ? filteringOption.map(({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`)
    : '';
  return axios.get(
    `${BASE_URL}/members/${username}/studylogs?${[...filterQuery, ...searchParams].join('&')}`
  );
};

export const requestGetCalendar = (year, month, username) =>
  axios.get(`${BASE_URL}/members/${username}/calendar-studylogs?year=${year}&month=${month}`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
  });

export const requestGetMyScrap = ({ username, accessToken, postQueryParams }) => {
  const searchParams = Object.entries(postQueryParams).map(([key, value]) => `${key}=${value}`);

  return axios.get(`${BASE_URL}/members/${username}/scrap?${[...searchParams].join('&')}`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

/* Filter 관련 request */
/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetFilters = () => axios.get(`${BASE_URL}/filters`);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetMissions = () => axios.get(`${BASE_URL}/missions`);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetTags = () => axios.get(`${BASE_URL}/tags`);

export const requestGetUserTags = (username) => axios.get(`${BASE_URL}/members/${username}/tags`);

/* User 관련 request */
export const loginRequest = ({ code }) =>
  axios.post(
    `${BASE_URL}/login/token`,
    { code },
    {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    }
  );

export const getUserProfileRequest = ({ accessToken }) =>
  axios.get(`${BASE_URL}/members/me`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestGetProfile = (username) => axios.get(`${BASE_URL}/members/${username}/profile`);

export const requestEditProfile = (data, accessToken) =>
  axios.put(`${BASE_URL}/members/${data.username}`, data, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestGetProfileIntroduction = (username) =>
  axios.get(`${BASE_URL}/members/${username}/profile-intro`);

export const requestEditProfileIntroduction = (username, data, accessToken) =>
  axios.put(`${BASE_URL}/members/${username}/profile-intro`, data, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

/* 사용자 리액션 관련 requests */
export const requestPostScrap = ({ username, accessToken, id: studylogId }) =>
  axios.post(
    `${BASE_URL}/members/${username}/scrap`,
    { studylogId },
    {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );

export const requestDeleteScrap = ({ username, accessToken, id }) =>
  axios.delete(`${BASE_URL}/members/${username}/scrap`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    params: {
      studylog: id,
    },
  });

export const requestPostLike = ({ accessToken, id }) =>
  axios.post(
    `${BASE_URL}/studylogs/${id}/likes`,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );

export const requestDeleteLike = ({ accessToken, id }) =>
  axios.delete(`${BASE_URL}/studylogs/${id}/likes`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestGetMatchedStudylogs = ({ accessToken, startDate, endDate }) =>
  axios.get(`${BASE_URL}/studylogs/me`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  });

export const requestImageUpload = (formData) => axios.post(`${BASE_URL}/images`, formData);
