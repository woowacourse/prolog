import { createAxiosInstance } from '../utils/axiosInstance';

const customAxios = (accessToken?: string) => createAxiosInstance({ accessToken });

export const requestGetArticles = ({ accessToken }) => customAxios(accessToken).get(`/articles`);

export const requestPostArticles = ({ accessToken, data }) =>
  customAxios(accessToken).post('/articles', data);
