import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import curriculums from '../fixtures/curriculums';
import { quizMock } from '../fixtures/quizzes';
import roadmapMock from '../fixtures/roadmap';

export const roadmapHandler = [
  rest.get(`${BASE_URL}/curriculums`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(curriculums));
  }),

  rest.get(`${BASE_URL}/roadmaps`, (req, res, ctx) => {
    const { url } = req;
    const curriculumId = url.searchParams.get('curriculumId');

    const keywordsList = roadmapMock.getKeywords();

    return res(ctx.status(200), ctx.json({ data: keywordsList }));
  }),

  rest.get(`${BASE_URL}/keywords/:keywordId`, (req, res, ctx) => {
    const {
      params: { keywordId },
    } = req;

    const keywordData = roadmapMock.findKeyword(keywordId);

    return res(ctx.status(200), ctx.json({ ...keywordData }));
  }),

  rest.get(`${BASE_URL}/quizzes`, (req, res, ctx) => {
    const { url } = req;
    const keywordId = url.searchParams.get('keywordId');

    return res(ctx.status(200), ctx.json(quizMock[Number(keywordId)]));
  }),
];
