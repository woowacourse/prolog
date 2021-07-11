const BASE_URL = process.env.REACT_APP_API_URL;

const requestGetPosts = () => fetch(`${BASE_URL}/posts`);

const requestGetPost = (postId) => fetch(`${BASE_URL}/posts/${postId}`);

const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

const requestGetTags = () => fetch(`${BASE_URL}/tags`);

const requestGetFilteredPosts = (filterList) => {
  const filterQuery = filterList.map(
    ({ filterType, filterDetailId }) => `${filterType}=${filterDetailId}`
  );

  return fetch(`${BASE_URL}/posts?${filterQuery.join('&')}`);
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

const requestGetProfile = (username) =>
  fetch(`${BASE_URL}/members/${username}`, {
    method: 'GET',
  });

const requestGetUserPosts = (username, accessToken) =>
  fetch(`${BASE_URL}/members/${username}/posts`, {
    method: 'GET',
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
  requestGetFilteredPosts,
  requestGetTags,
  requestEditPost,
  requestGetUserPosts,
  requestDeletePost,
  requestGetProfile,
};
