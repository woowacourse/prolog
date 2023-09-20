import { client } from '.';
import {
  ArticleBookmarkPutRequest,
  ArticleRequest,
  MetaOgRequest,
  MetaOgResponse,
} from '../models/Article';

export const requestGetArticles = () => client.get(`/articles`);

export const requestPostArticles = (body: ArticleRequest) => client.post('/articles', body);

export const requestGetMetaOg = ({ url }: MetaOgRequest) => {
  return client.get<MetaOgResponse>(`/meta-og?url=${url}`);
};

export const requestPutArticleBookmark = ({ articleId, bookmark }: ArticleBookmarkPutRequest) => {
  return client.put(`/articles/${articleId}/bookmark`, { checked: bookmark });
};

export const requestGetFilteredArticle = (course: string, bookmark: boolean) => {
  return client.get(`/articles?course=${course}&onlyBookmarked=${bookmark}`);
};
