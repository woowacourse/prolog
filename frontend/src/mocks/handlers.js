import { rest } from 'msw';
import { dummyBadgeList } from './fixture';
import { BASE_URL } from '../configs/environment';

export const handlers = [
  rest.get(`${BASE_URL}/members/:username/badges`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyBadgeList));
  }),
];
