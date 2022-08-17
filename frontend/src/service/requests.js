import { BASE_URL } from '../configs/environment';

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetStudylog = ({ id, accessToken }) => {
  if (accessToken) {
    return fetch(`${BASE_URL}/studylogs/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    });
  }

  return fetch(`${BASE_URL}/studylogs/${id}`);
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
    return fetch(`${BASE_URL}/studylogs?${query.data.toString()}`, authConfig);
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

    return fetch(
      `${BASE_URL}/studylogs?${[...filterQuery, ...searchParams].join('&')}`,
      authConfig
    );
  }

  return fetch(`${BASE_URL}/studylogs`, authConfig);
};

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestPostStudylog = ({ accessToken, data }) =>
  fetch(`${BASE_URL}/studylogs`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestEditStudylog = ({ id, data, accessToken }) =>
  fetch(`${BASE_URL}/studylogs/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestDeleteStudylog = ({ id, accessToken }) =>
  fetch(`${BASE_URL}/studylogs/${id}`, {
    method: 'DELETE',
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
  return fetch(
    `${BASE_URL}/members/${username}/studylogs?${[...filterQuery, ...searchParams].join('&')}`
  );
};

export const requestGetCalendar = (year, month, username) =>
  fetch(`${BASE_URL}/members/${username}/calendar-studylogs?year=${year}&month=${month}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
  });

export const requestGetMyScrap = ({ username, accessToken, postQueryParams }) => {
  const searchParams = Object.entries(postQueryParams).map(([key, value]) => `${key}=${value}`);

  return fetch(`${BASE_URL}/members/${username}/scrap?${[...searchParams].join('&')}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

/* Filter 관련 request */
/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

/* @deprecated 의존성 완전 삭제 이후 코드 삭제*/
export const requestGetTags = () => fetch(`${BASE_URL}/tags`);

export const requestGetUserTags = (username) => fetch(`${BASE_URL}/members/${username}/tags`);

/* User 관련 request */
export const loginRequest = ({ code }) =>
  fetch(`${BASE_URL}/login/token`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: JSON.stringify({ code }),
  });

export const getUserProfileRequest = ({ accessToken }) =>
  fetch(`${BASE_URL}/members/me`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestGetProfile = (username) => fetch(`${BASE_URL}/members/${username}/profile`);

export const requestEditProfile = (data, accessToken) =>
  fetch(`${BASE_URL}/members/${data.username}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestGetProfileIntroduction = (username) =>
  fetch(`${BASE_URL}/members/${username}/profile-intro`);

export const requestEditProfileIntroduction = (username, data, accessToken) =>
  fetch(`${BASE_URL}/members/${username}/profile-intro`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

/* Report 관련 requests */
export const requestGetReportList = ({ username, type, size = 10, page = 1 }) =>
  fetch(`${BASE_URL}/${username}/reports?type=${type}&page=${page}&size=${size}`, {
    method: 'GET',
  });

export const requestGetReport = (reportId) =>
  fetch(`${BASE_URL}/reports/${reportId}`, {
    method: 'GET',
  });

export const requestPostReport = (data, accessToken) =>
  fetch(`${BASE_URL}/reports`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestDeleteReport = (reportId, accessToken) =>
  fetch(`${BASE_URL}/reports/${reportId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestEditReport = (data, reportId, accessToken) =>
  fetch(`${BASE_URL}/reports/${reportId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestGetAbilities = (username, accessToken) =>
  fetch(`${BASE_URL}/members/${username}/abilities`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestAddAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestDeleteAbility = (accessToken, abilityId) =>
  fetch(`${BASE_URL}/abilities/${abilityId}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestEditAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities/${data.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestSetDefaultAbility = (accessToken, field) =>
  fetch(`${BASE_URL}/abilities/template/${field}`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

/* 사용자 리액션 관련 requests */
export const requestPostScrap = ({ username, accessToken, id: studylogId }) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({ studylogId }),
  });

export const requestDeleteScrap = ({ username, accessToken, id }) =>
  fetch(`${BASE_URL}/members/${username}/scrap?studylog=${id}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestPostLike = ({ accessToken, id }) =>
  fetch(`${BASE_URL}/studylogs/${id}/likes`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestDeleteLike = ({ accessToken, id }) =>
  fetch(`${BASE_URL}/studylogs/${id}/likes`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestGetMatchedStudylogs = ({ accessToken, startDate, endDate }) =>
  fetch(`${BASE_URL}/studylogs/me?startDate=${startDate}&endDate=${endDate}`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
