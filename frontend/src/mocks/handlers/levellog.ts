import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import { LevellogRequest } from '../../models/Levellogs';
import levellogs, { LEVELLOG_ITEM_PER_PAGE } from '../db/levellogs';

export const levellogHandler = [
  rest.get(`${BASE_URL}/levellogs`, (req, res, ctx) => {
    const page = Number(req.url.searchParams.get('page')) || 1;

    const rangedLevellogs = levellogs.data.slice(
      (page - 1) * LEVELLOG_ITEM_PER_PAGE,
      page * LEVELLOG_ITEM_PER_PAGE
    );

    return res(ctx.status(200), ctx.json({ ...levellogs, data: rangedLevellogs, currPage: page }));
  }),

  rest.post<LevellogRequest>(`${BASE_URL}/levellogs`, (req, res, ctx) => {
    const { body } = req;

    levellogs.data = [
      ...levellogs.data,
      {
        id: levellogs.data.length + 1,
        title: body.title,
        author: {
          id: 1,
          username: 'kwannee',
          nickname: '후이',
          role: 'CREW',
          imageUrl: 'https://avatars.githubusercontent.com/u/41886825?v=4',
        },
        content: body.content,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
        levelLogs: body.levelLogs.map((levelLog, idx) => ({
          id: idx,
          question: levelLog.question,
          answer: levelLog.answer,
        })),
      },
    ];

    levellogs.data.sort(
      (log1, log2) => Number(new Date(log2.createdAt)) - Number(new Date(log1.createdAt))
    );

    return res(ctx.status(200), ctx.json(levellogs));
  }),

  rest.get(`${BASE_URL}/levellogs/:id`, (req, res, ctx) => {
    const {
      params: { id },
    } = req;

    const numberId = Number(id);

    return res(
      ctx.status(200),
      ctx.json(levellogs.data.find((levellog) => levellog.id === numberId))
    );
  }),

  rest.delete(`${BASE_URL}/levellogs/:id`, (req, res, ctx) => {
    const {
      params: { id },
    } = req;

    const numberId = Number(id);

    levellogs.data = levellogs.data.filter((levellog) => levellog.id !== numberId);

    return res(ctx.status(200));
  }),

  rest.put(`${BASE_URL}/levellogs/:id`, (req, res, ctx) => {
    const {
      params: { id },
    } = req;

    const editedLevellog = req.body as any;

    levellogs.data[Number(id) - 1] = {
      ...levellogs.data[Number(id) - 1],
      title: editedLevellog.title,
      content: editedLevellog.content,
      levelLogs: editedLevellog.levelLogs,
    };

    return res(ctx.status(200), ctx.json(levellogs.data));
  }),
];
