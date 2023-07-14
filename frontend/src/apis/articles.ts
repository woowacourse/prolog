import { ArticleRequest } from '../models/Article';
import { createAxiosInstance } from '../utils/axiosInstance';

const customAxios = (accessToken?: string) => createAxiosInstance({ accessToken });

export const requestGetArticles = ({ accessToken }) => customAxios(accessToken).get(`/articles`);

export const requestPostArticles = ({
  accessToken,
  body,
}: {
  accessToken: string;
  body: ArticleRequest;
}) => customAxios(accessToken).post('/articles', body);
