import { KeywordResponse } from '../../models/Keywords';

export default {
  data: [
    // 세션 1
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
      childrenKeywords: [
        {
          keywordId: 2,
          sessionId: 1, // for mock
          name: 'let, const, var',
          // order: 1,
          importance: 5,
          parentKeywordId: 1,
          description: 'let, const, var',
          quizs: {
            data: [
              {
                quizId: 1,
                question: 'let, const, var',
              },
              {
                quizId: 2,
                question: 'let, const, var2',
              },
              {
                quizId: 3,
                question: 'let, const, var3',
              },
            ],
          },
          childrenKeywords: [
            {
              keywordId: 3,
              sessionId: 1, // for mock
              name: 'let',
              // order: 1,
              importance: 5,
              parentKeywordId: 2,
              description: 'let',
              quizs: {
                data: [
                  {
                    quizId: 1,
                    question: 'let',
                  },
                  {
                    quizId: 2,
                    question: 'let2',
                  },
                  {
                    quizId: 3,
                    question: 'let3',
                  },
                ],
              },
              childrenKeywords: {},
            },
            {
              keywordId: 4,
              sessionId: 1, // for mock
              name: 'const',
              // order: 1,
              importance: 5,
              parentKeywordId: 2,
              description: 'const',
              quizs: {
                data: [
                  {
                    quizId: 1,
                    question: 'const',
                  },
                  {
                    quizId: 2,
                    question: 'const2',
                  },
                  {
                    quizId: 3,
                    question: 'const3',
                  },
                ],
              },
              childrenKeywords: {},
            },
            {
              keywordId: 5,
              sessionId: 1, // for mock
              name: 'var',
              // order: 1,
              importance: 5,
              parentKeywordId: 2,
              description: 'var',
              quizs: {
                data: [
                  {
                    quizId: 1,
                    question: 'var',
                  },
                  {
                    quizId: 2,
                    question: 'var2',
                  },
                  {
                    quizId: 3,
                    question: 'var3',
                  },
                ],
              },
              childrenKeywords: {},
            },
          ],
        },
      ],
    },
    // 세션 2
    {
      keywordId: 6,
      sessionId: 2, // for mock
      name: 'React',
      // order: 1,
      importance: 5,
      parentKeywordId: null,
      description: 'React입니다.',
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
      childrenKeywords: [
        {
          keywordId: 7,
          sessionId: 2, // for mock
          name: 'lifecycle',
          // order: 1,
          importance: 5,
          parentKeywordId: 6,
          description: 'lifecycle 설명',
          quizs: {
            data: [
              {
                quizId: 1,
                question: 'lifecycle 퀴즈',
              },
              {
                quizId: 2,
                question: 'lifecycle2 퀴즈',
              },
              {
                quizId: 3,
                question: 'lifecycle3 퀴즈',
              },
            ],
          },
          childrenKeywords: [
            {
              keywordId: 8,
              sessionId: 2, // for mock
              name: 'mount',
              // order: 1,
              importance: 5,
              parentKeywordId: 7,
              description: 'mount 설명',
              quizs: {
                data: [
                  {
                    quizId: 1,
                    question: 'mount 퀴즈',
                  },
                  {
                    quizId: 2,
                    question: 'mount2 퀴즈',
                  },
                  {
                    quizId: 3,
                    question: 'mount3 퀴즈',
                  },
                ],
              },
              childrenKeywords: {},
            },
            {
              keywordId: 9,
              sessionId: 2, // for mock
              name: 'unmount',
              // order: 1,
              importance: 5,
              parentKeywordId: 7,
              description: 'unmount 설명',
              quizs: {
                data: [
                  {
                    quizId: 1,
                    question: 'unmount 퀴즈',
                  },
                  {
                    quizId: 2,
                    question: 'unmount 퀴즈2',
                  },
                  {
                    quizId: 3,
                    question: 'unmount 퀴즈3',
                  },
                ],
              },
              childrenKeywords: {},
            },
            {
              keywordId: 10,
              sessionId: 2, // for mock
              name: 'update',
              // order: 1,
              importance: 5,
              parentKeywordId: 7,
              description: 'update 설명',
              quizs: {
                data: [
                  {
                    quizId: 1,
                    question: 'update 퀴즈',
                  },
                  {
                    quizId: 2,
                    question: 'update 퀴즈2',
                  },
                  {
                    quizId: 3,
                    question: 'update 퀴즈3',
                  },
                ],
              },
              childrenKeywords: {},
            },
          ],
        },
      ],
    },
  ],
  // 5
  filterKeywordsBySession(sessionId: string | readonly string[]) {
    const filteredData = this.data.filter((item) => item.sessionId === Number(sessionId));

    return {
      data: filteredData.map(({ keywordId, name, importance, description, parentKeywordId }) => ({
        keywordId,
        name,
        importance,
        description,
        parentKeywordId,
      })),
    };
  },
  // 4
  findKeyword(keywordId: string | readonly string[]) {
    // data를 순회하면서, childrenKeywords를 순회하면서 해당 keyword가 있는지 확인한다.
    const data = this.data
      .map((depth1Item) => {
        // 1뎁스 순회
        if (depth1Item.keywordId === Number(keywordId)) {
          return depth1Item;
        }

        return depth1Item.childrenKeywords.map((depth2Item) => {
          // 2뎁스 순회
          if (depth2Item.keywordId === Number(keywordId)) {
            return depth2Item;
          }

          return depth2Item.childrenKeywords.map((depth3Item) => {
            // 3뎁스 순회
            if (depth3Item.keywordId === Number(keywordId)) {
              return depth3Item;
            }

            return undefined;
          });
        });
      })
      .find((item) => item !== undefined);

    return data;
  },
  // 6-1
  filterChildrenKeywords(keywordId: string | readonly string[]) {
    const data = this.data.find((depth1Item) => {
      return depth1Item.keywordId === Number(keywordId);
    })?.childrenKeywords;

    return {
      data,
    };
  },
  // 10
  findQuizs(keywordId: string | readonly string[]) {
    const data = this.data
      .map((depth1Item) => {
        // 1뎁스 순회
        if (depth1Item.keywordId === Number(keywordId)) {
          return depth1Item.quizs.data;
        }

        return depth1Item.childrenKeywords.map((depth2Item) => {
          // 2뎁스 순회
          if (depth2Item.keywordId === Number(keywordId)) {
            return depth2Item.quizs.data;
          }

          return depth2Item.childrenKeywords.map((depth3Item) => {
            // 3뎁스 순회
            if (depth3Item.keywordId === Number(keywordId)) {
              return depth3Item.quizs.data;
            }

            return undefined;
          });
        });
      })
      .find((item) => item !== undefined);

    return { keywordId, data };
  },
};

interface Mock {
  data: KeywordResponse[];
}
