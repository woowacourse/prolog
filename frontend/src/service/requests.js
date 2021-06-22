const BASE_URL = process.env.REACT_APP_API_URL;

const requestGetPosts = () => fetch(`${BASE_URL}/posts`);

const requestGetPost = (postId) => fetch(`${BASE_URL}/posts/${postId}`);

const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

const requestGetMissions = () => fetch(`${BASE_URL}/missions`);

const requestGetTags = () => fetch(`${BASE_URL}/tags`);

const requestGetFilteredPosts = (missionId) => fetch(`${BASE_URL}/posts?missions=${missionId}`);

const requestEditPost = (postId, data, accessToken) =>
  fetch(`${BASE_URL}/posts/${postId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(data),
  });

export {
  requestGetPosts,
  requestGetPost,
  requestGetFilters,
  requestGetMissions,
  requestGetFilteredPosts,
  requestGetTags,
  requestEditPost,
};
