import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import { LevellogRequest } from '../../models/Levellogs';
import levellogs from '../db/levellogs.json';

export const levellogHandler = [
  rest.get(`${BASE_URL}/levellogs`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(levellogs));
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
          username: 'hui',
          nickname: '후이',
          role: 'CREW',
          imageUrl: 'https://avatars.githubusercontent.com/u/52682603?v=4',
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
];
