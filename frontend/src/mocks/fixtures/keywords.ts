export default {
  data: [
    {
      keywordId: 1,
      sessionId: 1, // for mock
      name: 'JavaScript',
      // order: 1,
      importance: 5,
      parentKeywordId: null,
      description: '동적 타이핑, 스크립트 언어입니다.',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
    {
      keywordId: 2,
      sessionId: 1, // for mock
      name: 'JavaScript2',
      // order: 2,
      importance: 5,
      parentKeywordId: null,
      description: '동적 타이핑, 스크립트 언어입니다.2',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
    {
      keywordId: 3,
      sessionId: 1, // for mock
      name: '변수 선언 방법',
      // order: 2,
      importance: 5,
      parentKeywordId: null,
      description: '동적 타이핑, 스크립트 언어입니다.2',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
    {
      keywordId: 1,
      sessionId: 2, // for mock
      name: 'TypeScript',
      // order: 1,
      importance: 5,
      parentKeywordId: null,
      description: '정적 타이핑, 컴파일러입니다.',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
    {
      keywordId: 2,
      sessionId: 1, // for mock
      name: 'TypeScript2',
      // order: 2,
      importance: 5,
      parentKeywordId: null,
      description: '정적 타이핑, 컴파일러입니다.2',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
    {
      keywordId: 1,
      sessionId: 3, // for mock
      name: 'Java',
      // order: 1,
      importance: 5,
      parentKeywordId: null,
      description: '정적 타이핑 입니다.',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
    {
      keywordId: 2,
      sessionId: 3, // for mock
      name: 'Java2',
      // order: 2,
      importance: 5,
      parentKeywordId: null,
      description: '정적 타이핑 입니다.2',
      quizs: {
        data: [
          {
            quizId: 1,
            question: '무엇이 궁금하시나요?',
          },
          {
            quizId: 2,
            question: '무엇이 궁금하시나요?2',
          },
          {
            quizId: 3,
            question: '무엇이 궁금하시나요?3',
          },
        ],
      },
    },
  ],
  filterKeywordsBySession(sessionId: any) {
    return {
      data: this.data.filter((el) => el.sessionId === sessionId && el.parentKeywordId === null),
    };
  },
  findKeyword(keywordId: any) {
    return this.data.find((el) => el.sessionId === keywordId);
  },
  filterChildKeywords(parentKeywordId: any) {
    return {
      data: this.data.filter((el) => el.parentKeywordId === parentKeywordId),
    };
  },
  findQuizs(keywordId: any) {
    return this.findKeyword(keywordId)?.quizs;
  },
};
