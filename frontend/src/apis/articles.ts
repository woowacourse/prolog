import { client } from '.';
import { ArticleRequest } from '../models/Article';

export const requestGetArticles = () => client.get(`/articles`);

export const requestPostArticles = (body: ArticleRequest) => client.post('/articles', body);
