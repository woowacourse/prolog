import { client } from '.';
import { ArticleRequest, ArticleMetaOgRequest } from '../models/Article';

export const requestGetArticles = () => client.get(`/articles`);

export const requestPostArticles = (body: ArticleRequest) => client.post('/articles', body);

export const requestPostMetaOg = (body: ArticleMetaOgRequest) => {
  return client.post('/articles/url', body);
};
