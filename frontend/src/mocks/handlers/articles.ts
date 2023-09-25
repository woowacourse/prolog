import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import articles from '../db/articles.json';
import articlesFrontend from '../db/articles-frontend.json';
import articlesBackend from '../db/articles-backend.json';
import articlesAndroid from '../db/articles-android.json';
import metaOg from '../db/metaog.json';
import { ArticleType } from '../../models/Article';

const articleUrl = 'https://think0wise.tistory.com/107';

export const articlesHandler = [
  // rest.get(`${BASE_URL}/articles`, (req, res, ctx) => {
  //   return res(ctx.status(200), ctx.json(articles));
  // }),

  rest.get(`${BASE_URL}/meta-og?url=${articleUrl}`, async (req, res, ctx) => {
    const data = metaOg;

    return res(ctx.status(200), ctx.json(data));
  }),

  rest.post<ArticleType[]>(`${BASE_URL}/articles`, (req, res, ctx) => {
    const newArticle: ArticleType = {
      id: articles.length + 1,
      userName: '도밥',
      title: '직렬화, 역직렬화는 무엇일까?',
      url: 'https://think0wise.tistory.com/107',
      createdAt: '2023-07-08 16:48',
      isBookMarked: false,
      imageUrl:
        'https://plus.unsplash.com/premium_photo-1682088845396-1b310a002302?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8JUVDJTk1JTg0JUVEJThCJUIwJUVEJTgxJUI0fGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60',
    };

    articles.push(newArticle);
    return res(ctx.status(200), ctx.json(newArticle));
  }),

  rest.put(`${BASE_URL}/articles/:articleId/bookmark`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.get(`${BASE_URL}/articles`, (req, res, ctx) => {
    const course = req.url.searchParams.get('course') ?? 'all';
    const onlyBookmarked = req.url.searchParams.get('onlyBookmarked') as string;

    const filteredCourse = (course: string) => {
      if (course === 'all') return articles;
      if (course === 'frontend') return articlesFrontend;
      if (course === 'backend') return articlesBackend;
      if (course === 'android') return articlesAndroid;
    };

    if (onlyBookmarked === 'false') {
      return res(ctx.status(200), ctx.json(filteredCourse(course)));
    }

    const filteredArticle = filteredCourse(course)?.filter((article) => {
      return onlyBookmarked === String(article.isBookMarked) ? true : false;
    });

    return res(ctx.status(200), ctx.json(filteredArticle));
  }),
];
