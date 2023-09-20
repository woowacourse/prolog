import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import articles from '../db/articles.json';
import metaOg from '../db/metaog.json';
import { ArticleType } from '../../models/Article';

const articleUrl = 'https://think0wise.tistory.com/107';

export const articlesHandler = [
  rest.get(`${BASE_URL}/articles`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(articles));
  }),

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

  rest.get(`${BASE_URL}/articles/`, (req, res, ctx) => {
    const course = req.url.searchParams.get('course');
    const onlyBookmarked = req.url.searchParams.get('onlyBookmarked');

    const filteredArticle = articles.filter(
      (article) => onlyBookmarked === String(article.isBookMarked)
    );

    return res(ctx.status(200), ctx.json(filteredArticle));
  }),
];
