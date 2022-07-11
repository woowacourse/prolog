import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import popularStudyLog from '../db/popularStudyLog.json';

export const popularStudyLogHandler = [
  rest.get(`${BASE_URL}/studylogs/popular`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(popularStudyLog));
  }),
];
