import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';

export const profileHandler = [
  rest.post(`${BASE_URL}/members/promote`, (req, res, ctx) => {
    return res(ctx.status(201), ctx.json({}));
  }),
];
