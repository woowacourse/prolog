import { client } from '.';
import { ArticleRequest, MetaOgRequest, MetaOgResponse } from '../models/Article';

export const requestGetArticles = () => client.get(`/articles`);

export const requestPostArticles = (body: ArticleRequest) => client.post('/articles', body);

export const requestGetMetaOg = (url: MetaOgRequest) => {
  return client.get<MetaOgResponse>(`/meta-og?url=${url}`);
};
