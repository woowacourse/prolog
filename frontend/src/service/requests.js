const BASE_URL = process.env.REACT_APP_API_URL;

/* 학습로그 관련 요청 */
export const requestGetStudylog = (studylogId, accessToken) => {
  if (accessToken) {
    return fetch(`${BASE_URL}/studylogs/${studylogId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    });
  }

  return fetch(`${BASE_URL}/studylogs/${studylogId}`);
};

export const requestGetStudylogs = (query, accessToken) => {
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
    const searchParams = Object.entries(query?.data?.studylogQueryParams).map(
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

export const requestPostStudylog = (posts, accessToken) =>
  fetch(`${BASE_URL}/studylogs`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(posts),
  });

export const requestEditStudylog = (studylogId, data, accessToken) =>
  fetch(`${BASE_URL}/studylogs/${studylogId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestDeleteStudylog = (studylogId, accessToken) =>
  fetch(`${BASE_URL}/studylogs/${studylogId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

/* 학습로그 필터링 */

export const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

export const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

export const requestGetTags = () => fetch(`${BASE_URL}/tags`);

/* 프로필 */

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

export const requestGetUserStudylogs = (username, studylogsearchParams, filteringOption) => {
  const searchParams = Object.entries(studylogsearchParams).map(
    ([key, value]) => `${key}=${value}`
  );
  const filterQuery = filteringOption.length
    ? filteringOption.map(({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`)
    : '';
  return fetch(
    `${BASE_URL}/members/${username}/studylogs?${[...filterQuery, ...searchParams].join('&')}`
  );
};

export const requestGetUserTags = (username) => fetch(`${BASE_URL}/members/${username}/tags`);

export const requestGetCalendar = (year, month, username) =>
  fetch(`${BASE_URL}/members/${username}/calendar-studylogs?year=${year}&month=${month}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
  });

/*리포트*/

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

/* 역량 */

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

/* 사용자 리액션 */

export const requestPostScrap = (username, accessToken, data) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestDeleteScrap = (username, accessToken, data) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestGetMyScrap = (username, accessToken, studylogQueryParams) => {
  const searchParams = Object.entries(studylogQueryParams).map(([key, value]) => `${key}=${value}`);

  return fetch(`${BASE_URL}/members/${username}/scrap?${[...searchParams].join('&')}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

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

export const requestPostLike = (accessToken, studylogId) =>
  fetch(`${BASE_URL}/studylogs/${studylogId}/likes`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export const requestDeleteLike = (accessToken, studylogId) =>
  fetch(`${BASE_URL}/studylogs/${studylogId}/likes`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export default {
  requestGetStudylog,
  requestGetStudylogs,
  requestGetFilters,
  requestGetMissions,
  requestGetTags,
  requestEditStudylog,
  requestGetUserStudylogs,
  requestDeleteStudylog,
  requestGetProfile,
  requestEditProfile,
  requestGetUserTags,
  requestGetCalendar,
  requestGetAbilities,
  requestAddAbility,
  requestDeleteAbility,
  requestEditAbility,
  requestSetDefaultAbility,
  requestGetReportList,
  requestGetReport,
  requestPostReport,
  requestDeleteReport,
  requestEditReport,
  requestPostScrap,
  requestDeleteScrap,
  requestGetMyScrap,
  requestPostLike,
  requestDeleteLike,
};
