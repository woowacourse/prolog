import { BASE_URL } from '../configs/environment';

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

  return fetch(`${BASE_URL}/posts/${id}`);
};

export const requestPostStudylog = ({ accessToken, data }) =>
  fetch(`${BASE_URL}/posts`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify([data]),
  });

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

const requestGetStudylogs = (query, accessToken) => {
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

export const requestEditStudylog = ({ id, data, accessToken }) =>
  fetch(`${BASE_URL}/studylogs/${id}`, {
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

export const requestDeleteStudylog = ({ id, accessToken }) =>
  fetch(`${BASE_URL}/studylogs/${id}`, {
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

const requestGetAbilities = (username, accessToken) =>
  fetch(`${BASE_URL}/members/${username}/abilities`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestAddAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

const requestPostScrap = ({ username, accessToken, id: studylogId }) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({ studylogId }),
  });

const requestDeleteScrap = ({ username, accessToken, id: studylogId }) =>
  fetch(`${BASE_URL}/members/${username}/scrap`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({ studylogId }),
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

const requestDeleteAbility = (accessToken, abilityId) =>
  fetch(`${BASE_URL}/abilities/${abilityId}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

const requestEditAbility = (accessToken, data) =>
  fetch(`${BASE_URL}/abilities/${data.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
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

export const requestStudylogLike = ({ accessToken, id }) =>
  fetch(`${BASE_URL}/studylogs/${id}/likes`, {
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

const requestGetProfileIntroduction = (username) =>
  fetch(`${BASE_URL}/members/${username}/profile-intro`);

const requestEditProfileIntroduction = (username, data, accessToken) =>
  fetch(`${BASE_URL}/members/${username}/profile-intro`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export {
  requestGetPost,
  requestGetPosts,
  requestGetStudylogs,
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
  requestGetProfileIntroduction,
  requestEditProfileIntroduction,
};
