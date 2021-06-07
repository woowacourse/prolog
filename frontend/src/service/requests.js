const BASE_URL = process.env.REACT_APP_API_URL;

const getPosts = () => fetch(`${BASE_URL}/posts`);

const getPost = (id) => fetch(`${BASE_URL}/posts/${id}`);

export { getPosts, getPost };
