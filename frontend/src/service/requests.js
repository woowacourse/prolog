const BASE_URL = process.env.REACT_APP_API_URL;

const requestGetPosts = () => fetch(`${BASE_URL}/posts`);

const requestGetPost = (postId) => fetch(`${BASE_URL}/posts/${postId}`);

const requestGetFilters = () => fetch(`${BASE_URL}/filters`);

const requestGetFilteredPosts = (missionId) => fetch(`${BASE_URL}/posts?missions=${missionId}`);

export { requestGetPosts, requestGetPost, requestGetFilters, requestGetFilteredPosts };
