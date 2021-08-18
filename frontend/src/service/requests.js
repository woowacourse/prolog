const BASE_URL = process.env.REACT_APP_API_URL;

const requestGetPost = (postId) => fetch(`${BASE_URL}/posts/${postId}`);

const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

const requestGetTags = () => fetch(`${BASE_URL}/tags`);

const requestGetPosts = (filterList, postSearchParams) => {
  const filterQuery = filterList.map(
    ({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`
  );
  const searchParams = Object.entries(postSearchParams).map(([key, value]) => `${key}=${value}`);

  return fetch(`${BASE_URL}/posts?${[...filterQuery, ...searchParams].join('&')}`);
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

const requestGetUserPosts = (username, postSearchParams, filteringOption) => {
  const searchParams = Object.entries(postSearchParams).map(([key, value]) => `${key}=${value}`);
  const filterQuery = Object.entries(filteringOption).length
    ? `&${Object.entries(filteringOption)[0].join('=')}`
    : '';

  return fetch(`${BASE_URL}/members/${username}/posts?${searchParams.join('&')}${filterQuery}`);
};

const requestGetUserTags = (username) => fetch(`${BASE_URL}/members/${username}/tags`);

const requestGetCalendar = (year, month, username) =>
  fetch(`${BASE_URL}/members/${username}/calendar-posts?year=${year}&month=${month}`, {
    method: 'GET',
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
  requestGetUserTags,
  requestGetCalendar,
};
