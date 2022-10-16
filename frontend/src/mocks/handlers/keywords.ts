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

  /** 4. 키워드 단건 조회. 2, 3 depth */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords/:keywordId`, (req, res, ctx) => {
    const {
      params: { sessionId, keywordId },
    } = req;

    const keywordData = keywordsMock.findKeyword(keywordId);

    return res(ctx.status(200), ctx.json({ ...keywordData }));
  }),

  /** 직계 하위 키워드 조회 */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords/:keywordId/children`, (req, res, ctx) => {
    const {
      params: { sessionId, keywordId },
    } = req;

    const keywordData = keywordsMock.findKeyword(keywordId);

    if (!keywordData) {
      return res(ctx.status(400), ctx.json({ message: '해당 keyword가 없습니다.' }));
    }

    const { parentKeywordId } = keywordData;

    const childKeywords = keywordsMock.filterChildKeywords(parentKeywordId);

    return res(ctx.status(200), ctx.json({ ...childKeywords }));
  }),

  /** 키워드별 Quiz 조회 */
  rest.get(`${BASE_URL}/sessions/:sessionId/keywords/:keywordId/quizs`, (req, res, ctx) => {
    const {
      params: { sessionId, keywordId },
    } = req;

    const quizs = keywordsMock.findQuizs(keywordId);

    return res(ctx.status(200), ctx.json({ ...quizs }));
  }),
];
