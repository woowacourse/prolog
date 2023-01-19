import { quizMock } from './quizs';

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
        data: quizMock[1].data,
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
            data: quizMock[2].data,
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
                data: quizMock[3].data,
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
                data: quizMock[4].data,
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
                data: quizMock[5].data,
              },
              childrenKeywords: {},
            },
          ],
        },
      ],
    },
    // 세션 1 - React 키워드
    {
      keywordId: 6,
      sessionId: 1, // for mock
      name: 'React',
      // order: 1,
      importance: 5,
      parentKeywordId: null,
      description: 'React입니다.',
      quizs: {
        data: quizMock[6].data,
      },
      childrenKeywords: [
        {
          keywordId: 7,
          sessionId: 1, // for mock
          name: 'lifecycle',
          // order: 1,
          importance: 5,
          parentKeywordId: 6,
          description: 'lifecycle 설명',
          quizs: {
            data: quizMock[7].data,
          },
          childrenKeywords: [
            {
              keywordId: 8,
              sessionId: 1, // for mock
              name: 'mount',
              // order: 1,
              importance: 5,
              parentKeywordId: 7,
              description: 'mount 설명',
              quizs: {
                data: quizMock[8].data,
              },
              childrenKeywords: {},
            },
            {
              keywordId: 9,
              sessionId: 1, // for mock
              name: 'unmount',
              // order: 1,
              importance: 5,
              parentKeywordId: 7,
              description: 'unmount 설명',
              quizs: {
                data: quizMock[9].data,
              },
              childrenKeywords: {},
            },
            {
              keywordId: 10,
              sessionId: 1, // for mock
              name: 'update',
              // order: 1,
              importance: 5,
              parentKeywordId: 7,
              description: 'update 설명',
              quizs: {
                data: quizMock[10].data,
              },
              childrenKeywords: {},
            },
          ],
        },
      ],
    },
    // 세션 2 - Test
    {
      keywordId: 11,
      sessionId: 2, // for mock
      name: 'Test',
      // order: 1,
      importance: 5,
      parentKeywordId: null,
      description: 'Test입니다.',
      quizs: {
        data: quizMock[11].data,
      },
      childrenKeywords: [
        {
          keywordId: 12,
          sessionId: 2, // for mock
          name: 'Jest',
          // order: 1,
          importance: 5,
          parentKeywordId: 11,
          description: 'Jest 설명',
          quizs: {
            data: quizMock[12].data,
          },
          childrenKeywords: [
            {
              keywordId: 13,
              sessionId: 2, // for mock
              name: 'ReactTestingLibrary',
              // order: 1,
              importance: 5,
              parentKeywordId: 12,
              description: 'ReactTestingLibrary 설명',
              quizs: {
                data: quizMock[13].data,
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
      data: filteredData,
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
    const childrenKeywords = this.data.find((depth1Item) => {
      return depth1Item.keywordId === Number(keywordId);
    })?.childrenKeywords;

    return {
      childrenKeywords,
    };
  },
};
