import { BASE_URL } from '../../configs/environment';
import { rest } from 'msw';
import keywordsMock from '../fixtures/keywords';

export const roadmapHandler = [
  /** 5. 세션별 키워드 목록 조회. 1 depth */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords`, (req, res, ctx) => {
    const {
      params: { sessionId },
    } = req;

    const keywordsList = keywordsMock.filterKeywordsBySession(sessionId);

    return res(ctx.status(200), ctx.json({ ...keywordsList }));
  }),

  /** 4. 키워드 단건 조회. 1, 2, 3 depth */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords/:keywordId`, (req, res, ctx) => {
    const {
      params: { sessionId, keywordId },
    } = req;

    const keywordData = keywordsMock.findKeyword(keywordId);

    return res(ctx.status(200), ctx.json({ ...keywordData }));
  }),

  /** 6-1. 1 -> 2,3 키워드 조회 */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords/:keywordId/children`, (req, res, ctx) => {
    const {
      params: { sessionId, keywordId },
    } = req;

    const childrenKeywords = keywordsMock.filterChildrenKeywords(keywordId);

    return res(ctx.status(200), ctx.json({ ...childrenKeywords }));
  }),

  /** 10. 키워드별 Quiz 조회 */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords/:keywordId/quizs`, (req, res, ctx) => {
    const {
      params: { sessionId, keywordId },
    } = req;

    const quizs = keywordsMock.findQuizs(keywordId);

    return res(ctx.status(200), ctx.json({ ...quizs }));
  }),
];
