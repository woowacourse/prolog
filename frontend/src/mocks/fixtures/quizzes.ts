import { Quiz } from '../../models/Keywords';

const data: Array<{
  keywordId: number;
  data: Quiz[];
}> = [
  {
    keywordId: 0,
    data: [],
  },
  {
    keywordId: 1,
    data: [
      {
        quizId: 1,
        question: 'JavaScript 무엇이 궁금하시나요?',
      },
      {
        quizId: 2,
        question: 'JavaScript 무엇이 궁금하시나요?2',
      },
      {
        quizId: 3,
        question: 'JavaScript 무엇이 궁금하시나요?3',
      },
    ],
  },
  {
    keywordId: 2,
    data: [
      {
        quizId: 4,
        question: 'let, const, var',
      },
      {
        quizId: 5,
        question: 'let, const, var2',
      },
      {
        quizId: 6,
        question: 'let, const, var3',
      },
    ],
  },
  {
    keywordId: 3,
    data: [
      {
        quizId: 7,
        question: 'let 퀴즈1',
      },
      {
        quizId: 8,
        question: 'let 퀴즈2',
      },
      {
        quizId: 9,
        question: 'let 퀴즈3',
      },
    ],
  },
  {
    keywordId: 4,
    data: [
      {
        quizId: 10,
        question: 'const 퀴즈1',
      },
      {
        quizId: 11,
        question: 'const 퀴즈2',
      },
      {
        quizId: 12,
        question: 'const 퀴즈3',
      },
    ],
  },
  {
    keywordId: 5,
    data: [
      {
        quizId: 13,
        question: 'var 퀴즈1',
      },
      {
        quizId: 14,
        question: 'var 퀴즈2',
      },
      {
        quizId: 15,
        question: 'var 퀴즈3',
      },
    ],
  },
  {
    keywordId: 6,
    data: [
      {
        quizId: 16,
        question: 'react 퀴즈1',
      },
      {
        quizId: 17,
        question: 'react 퀴즈2',
      },
      {
        quizId: 18,
        question: 'react 퀴즈3',
      },
    ],
  },
  {
    keywordId: 7,
    data: [
      {
        quizId: 19,
        question: 'lifecycle 퀴즈1',
      },
      {
        quizId: 20,
        question: 'lifecycle 퀴즈2',
      },
      {
        quizId: 21,
        question: 'lifecycle 퀴즈3',
      },
    ],
  },
  {
    keywordId: 8,
    data: [
      {
        quizId: 22,
        question: 'mount 퀴즈1',
      },
      {
        quizId: 23,
        question: 'mount 퀴즈2',
      },
      {
        quizId: 24,
        question: 'mount 퀴즈3',
      },
    ],
  },
  {
    keywordId: 9,
    data: [
      {
        quizId: 25,
        question: 'unmount 퀴즈1',
      },
      {
        quizId: 26,
        question: 'unmount 퀴즈2',
      },
      {
        quizId: 27,
        question: 'unmount 퀴즈3',
      },
    ],
  },
  {
    keywordId: 10,
    data: [
      {
        quizId: 28,
        question: 'update 퀴즈1',
      },
      {
        quizId: 29,
        question: 'update 퀴즈2',
      },
      {
        quizId: 30,
        question: 'update 퀴즈3',
      },
    ],
  },
  {
    keywordId: 11,
    data: [
      {
        quizId: 31,
        question: 'Test 퀴즈1',
      },
      {
        quizId: 32,
        question: 'Test 퀴즈2',
      },
      {
        quizId: 33,
        question: 'Test 퀴즈3',
      },
    ],
  },
  {
    keywordId: 12,
    data: [
      {
        quizId: 34,
        question: 'Jest 퀴즈1',
      },
      {
        quizId: 35,
        question: 'Jest 퀴즈2',
      },
      {
        quizId: 36,
        question: 'Jest 퀴즈3',
      },
    ],
  },
  {
    keywordId: 13,
    data: [
      {
        quizId: 37,
        question: 'ReactTestingLibrary 퀴즈1',
      },
      {
        quizId: 38,
        question: 'ReactTestingLibrary 퀴즈2',
      },
      {
        quizId: 39,
        question: 'ReactTestingLibrary 퀴즈3',
      },
    ],
  },
];

const quizMock = {
  data,
  findQuiz(quizId) {
    return this.data.flatMap(({ data }) => data).find((quiz) => quiz.quizId === quizId) ?? null;
  },
  filterByKeyword(keywordId) {
    return this.data.find((quizs) => quizs.keywordId === keywordId)?.data ?? [];
  },
};

export default quizMock;
