import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import articles from '../db/articles.json';

// 추후 articles type models에 정의 후 삭제
export interface Articles {
  id: number;
  userName: string;
  title: string;
  url: string;
  createdAt: string;
}

export const articlesHandler = [
  rest.get(`${BASE_URL}/articles`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(articles));
  }),

  rest.post<Articles[]>(`${BASE_URL}/articles`, (req, res, ctx) => {
    const newArticle: Articles = {
      id: articles.length + 1,
      userName: '도밥',
      title: '직렬화, 역직렬화는 무엇일까?',
      url: 'https://think0wise.tistory.com/107',
      createdAt: '2023-07-08',
    };

    articles.push(newArticle);
    return res(ctx.status(200), ctx.json(newArticle));
  }),
];
