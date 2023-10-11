import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import essayAnswersMock from '../fixtures/essayAnswers';

export const essayAnswerHandler = [
  rest.get(`${BASE_URL}/essay-answers`, (req, res, ctx) => {
    const { searchParams } = req.url;

    const params = {
      curriculumId: searchParams.get('curriculumId'),
      keywordId: searchParams.get('keywordId'),
      quizIds: searchParams.get('quizIds'),
      memberIds: searchParams.get('memberIds'),
      page: searchParams.get('page'),
      size: searchParams.get('size'),
    };

    const curriculumId = Number(params.curriculumId);
    if (Number.isNaN(curriculumId)) {
      return res(ctx.status(400));
    }

    return res(
      ctx.status(200),
      ctx.json(
        essayAnswersMock.filter({
          curriculumId: curriculumId,
          keywordId: params.keywordId ? Number(params.keywordId) : undefined,
          quizIds: params.quizIds
            ? params.quizIds.slice(1, -1).split(',').map(Number) ?? []
            : undefined,
          memberIds: params.memberIds
            ? params.memberIds.slice(1, -1).split(',').map(Number) ?? []
            : undefined,
          page: params.page ? Number(params.page) : undefined,
          size: params.size ? Number(params.size) : undefined,
        })
      )
    );
  }),
];
