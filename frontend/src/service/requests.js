const BASE_URL = process.env.REACT_APP_API_URL;

const getPosts = () => fetch(`${BASE_URL}/posts`);

const getPost = (id) => fetch(`${BASE_URL}/posts/${id}`);

const getFilters = () => fetch(`${BASE_URL}/filters`);

const getFilteredPosts = (missionId) => fetch(`${BASE_URL}/posts?missions=${missionId}`);

export { getPosts, getPost, getFilters, getFilteredPosts };
