const BASE_URL = process.env.REACT_APP_API_URL;

/*
 * @deprecated
 * post -> studylog로 변환 작업중
 * 추후 의존성이 모두 제거되면 해당 함수 삭제 예정
 */
const requestGetPost = (postId, accessToken) => {
  if (accessToken) {
    return fetch(`${BASE_URL}/posts/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    });
  }

  return fetch(`${BASE_URL}/posts/${postId}`);
};

const requestGetStudyLog = (postId, accessToken) => {
  if (accessToken) {
    return fetch(`${BASE_URL}/posts/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    });
  }

  return fetch(`${BASE_URL}/posts/${postId}`);
};

const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

const requestGetTags = () => fetch(`${BASE_URL}/tags`);

/*
 * @deprecated
 * post -> studylog로 변환 작업중
 * 추후 의존성이 모두 제거되면 해당 함수 삭제 예정
 */
const requestGetPosts = (query, accessToken) => {
  const authConfig = accessToken
    ? {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    : {};

  if (query.type === 'searchParams') {
    return fetch(`${BASE_URL}/posts?${query.data.toString()}`, authConfig);
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

    return fetch(`${BASE_URL}/posts?${[...filterQuery, ...searchParams].join('&')}`, authConfig);
  }

  return fetch(`${BASE_URL}/posts`, authConfig);
};

const requestGetStudyLogs = (query, accessToken) => {
  const authConfig = accessToken
    ? {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    : {};

  if (query.type === 'searchParams') {
    return fetch(`${BASE_URL}/posts?${query.data.toString()}`, authConfig);
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

    return fetch(`${BASE_URL}/posts?${[...filterQuery, ...searchParams].join('&')}`, authConfig);
  }

  return fetch(`${BASE_URL}/posts`, authConfig);
};

const requestEditPost = (postId, data, accessToken) =>
  fetch(`${BASE_URL}/posts/${postId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestDeletePost = (postId, accessToken) =>
  fetch(`${BASE_URL}/posts/${postId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestGetProfile = (username) => fetch(`${BASE_URL}/members/${username}/profile`);

const requestEditProfile = (data, accessToken) =>
  fetch(`${BASE_URL}/members/${data.username}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestGetUserPosts = (username, postSearchParams, filteringOption) => {
  const searchParams = Object.entries(postSearchParams).map(([key, value]) => `${key}=${value}`);
  const filterQuery = filteringOption.length
    ? filteringOption.map(({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`)
    : '';
  return fetch(
    `${BASE_URL}/members/${username}/posts?${[...filterQuery, ...searchParams].join('&')}`
  );
};

const requestGetUserTags = (username) => fetch(`${BASE_URL}/members/${username}/tags`);

const requestGetCalendar = (year, month, username) =>
  fetch(`${BASE_URL}/members/${username}/calendar-posts?year=${year}&month=${month}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
  });

const requestGetReportList = ({ username, type, size = 10, page = 1 }) =>
  fetch(`${BASE_URL}/${username}/reports?type=${type}&page=${page}&size=${size}`, {
    method: 'GET',
  });

const requestGetReport = (reportId) =>
  fetch(`${BASE_URL}/reports/${reportId}`, {
    method: 'GET',
  });

const requestPostReport = (data, accessToken) =>
  fetch(`${BASE_URL}/reports`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestDeleteReport = (reportId, accessToken) =>
  fetch(`${BASE_URL}/reports/${reportId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestEditReport = (data, reportId, accessToken) =>
  fetch(`${BASE_URL}/reports/${reportId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestPostScrap = (username, accessToken, data) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestDeleteScrap = (username, accessToken, data) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestGetMyScrap = (username, accessToken, postQueryParams) => {
  const searchParams = Object.entries(postQueryParams).map(([key, value]) => `${key}=${value}`);

  return fetch(`${BASE_URL}/members/${username}/scrap?${[...searchParams].join('&')}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

const requestGetAbilities = (username) =>
  fetch(`${BASE_URL}/members/${username}/abilities`, {
    method: 'GET',
  });

/*
 * @deprecated
 * 역량 정책이 달라짐에 따라서 duplicated가 확정이 되면 삭제해야함.
 */
const requestAddAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export const requestPutAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

/*
 * @deprecated
 * 역량 정책이 달라짐에 따라서 duplicated가 확정이 되면 삭제해야함.
 */
const requestDeleteAbility = (accessToken, abilityId) =>
  fetch(`${BASE_URL}/abilities/${abilityId}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

/*
 * @deprecated
 * 역량 정책이 달라짐에 따라서 duplicated가 확정이 되면 삭제해야함.
 */
const requestEditAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities/${data.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestGetAbilityHistory = (accessToken, abilityId) =>
  fetch(`${BASE_URL}/abilities-history/${abilityId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestGetAbilityHistories = (accessToken) =>
  fetch(`${BASE_URL}/abilities-histories`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestDeleteAbilityHistory = (accessToken, abilityId) =>
  fetch(`${BASE_URL}/abilities-history/${abilityId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestSetDefaultAbility = (accessToken, field) =>
  fetch(`${BASE_URL}/abilities/template/${field}`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestPostLike = (accessToken, postId) =>
  fetch(`${BASE_URL}/studylogs/${postId}/likes`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestDeleteLike = (accessToken, postId) =>
  fetch(`${BASE_URL}/studylogs/${postId}/likes`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

export {
  requestGetPost,
  requestGetPosts,
  requestGetStudyLog,
  requestGetStudyLogs,
  requestGetFilters,
  requestGetMissions,
  requestGetTags,
  requestEditPost,
  requestGetUserPosts,
  requestDeletePost,
  requestGetProfile,
  requestEditProfile,
  requestGetUserTags,
  requestGetCalendar,
  requestGetAbilities,
  requestAddAbility,
  requestDeleteAbility,
  requestEditAbility,
  requestGetAbilityHistory,
  requestGetAbilityHistories,
  requestDeleteAbilityHistory,
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
