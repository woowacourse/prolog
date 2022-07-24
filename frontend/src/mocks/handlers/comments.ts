import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import comments from '../db/comments.json';

export const commentsHandler = [
  rest.get(`${BASE_URL}/studylogs/:studylogId/comments`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(comments));
  }),
];
