import { client } from '.';
import {
  ArticleBookmarkPutRequest,
  ArticleLikePutRequest,
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
  return client.put(`/articles/${articleId}/bookmark`, { bookmark });
};

export const requestGetFilteredArticle = (course: string, bookmark: boolean) => {
  return client.get(`/articles?course=${course}&onlyBookmarked=${bookmark}`);
};

export const requestPutArticleLike = ({ articleId, like }: ArticleLikePutRequest) => {
  return client.put(`/articles/${articleId}/like`, { like });
};

export const requestPostArticleView = (articleId: number) => {
  return client.post(`/articles/${articleId}/views`);
};
