import { rest } from 'msw';
import { BASE_URL } from '../../configs/environment';
import { CommentRequest } from '../../models/Comment';
import comments from '../db/comments.json';

export const commentsHandler = [
  rest.get(`${BASE_URL}/studylogs/:studylogId/comments`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(comments));
  }),
  rest.post<CommentRequest>(`${BASE_URL}/studylogs/:studylogId/comments`, (req, res, ctx) => {
    const { body } = req;

    comments.data = [
      ...comments.data,
      {
        id: comments.data.length + 1,
        author: {
          id: 1,
          username: 'euijinkk',
          nickname: '시지프',
          imageUrl: 'https://avatars.githubusercontent.com/u/24906022?v=4',
          role: 'crew',
        },
        content: body.content,
        createAt: '2022-07-24',
      },
    ];

    return res(ctx.status(201));
  }),
  rest.put<CommentRequest>(
    `${BASE_URL}/studylogs/:studylogId/comments/:commentId`,
    (req, res, ctx) => {
      const {
        body: { content },
        params: { commentId },
      } = req;

      if (typeof Number(commentId) !== 'number' || typeof commentId !== 'string') {
        return res(ctx.status(400));
      }

      const editedComments = comments.data.map((comment) =>
        comment.id === Number(commentId) ? { ...comment, content } : comment
      );

      comments.data = editedComments;

      return res(ctx.status(204));
    }
  ),
  rest.delete(`${BASE_URL}/studylogs/:studylogId/comments/:commentId`, (req, res, ctx) => {
    const {
      params: { commentId },
    } = req;

    if (typeof Number(commentId) !== 'number' || typeof commentId !== 'string') {
      return res(ctx.status(400));
    }

    const omittedComments = comments.data.filter((comment) => comment.id !== Number(commentId));

    comments.data = omittedComments;

    return res(ctx.status(204));
  }),
];
