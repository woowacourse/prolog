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

export {
  requestGetPosts,
  requestGetPost,
  requestGetFilters,
  requestGetMissions,
  requestGetFilteredPosts,
  requestGetTags,
};
