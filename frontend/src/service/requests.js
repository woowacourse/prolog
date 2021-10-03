const BASE_URL = process.env.REACT_APP_API_URL;

const requestGetPost = (postId) => fetch(`${BASE_URL}/posts/${postId}`);

const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

const requestGetTags = () => fetch(`${BASE_URL}/tags`);

const requestGetPosts = (query) => {
  if (query.type === 'searchParams') {
    return fetch(`${BASE_URL}/posts?${query.data.toString()}`);
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

    return fetch(`${BASE_URL}/posts?${[...filterQuery, ...searchParams].join('&')}`);
  }
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

const requestGetReportList = (username) =>
  fetch(`${BASE_URL}/${username}/reports?type=simple`, {
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

export {
  requestGetPosts,
  requestGetPost,
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
  requestGetReportList,
  requestGetReport,
  requestPostReport,
  requestDeleteReport,
};
