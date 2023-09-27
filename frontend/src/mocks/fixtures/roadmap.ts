import { RoadmapResponse } from '../../models/Keywords';

const roadmapData: RoadmapResponse['data'] = [
  // 세션 1
  {
    keywordId: 1,
    name: 'JavaScript',
    order: 1,
    importance: 4,
    parentKeywordId: null,
    description: '동적 타이핑, 스크립트 언어입니다.',
    doneQuizCount: 4,
    totalQuizCount: 5,
    recommendedPosts: [
      {
        id: 1,
        url: 'https://solo5star.tistory.com',
      },
      {
        id: 2,
        url: 'https://solo5star.tistory.com',
      },
      {
        id: 3,
        url: 'https://solo5star.tistory.com',
      },
      {
        id: 4,
        url: 'https://solo5star.tistory.com',
      },
      {
        id: 5,
        url: 'https://solo5star.tistory.com',
      },
    ],
    childrenKeywords: [
      {
        keywordId: 2,
        name: 'let, const, var',
        order: 1,
        importance: 4,
        parentKeywordId: 1,
        description: 'let, const, var',
        doneQuizCount: 0,
        totalQuizCount: 0,
        recommendedPosts: [],
        childrenKeywords: [
          {
            keywordId: 3,
            name: 'let',
            order: 1,
            importance: 4,
            parentKeywordId: 2,
            description: 'let',
            doneQuizCount: 1,
            totalQuizCount: 4,
            recommendedPosts: [],
            childrenKeywords: [],
          },
          {
            keywordId: 4,
            name: 'const',
            order: 1,
            importance: 4,
            parentKeywordId: 2,
            description: 'const',
            doneQuizCount: 3,
            totalQuizCount: 3,
            recommendedPosts: [
              {
                id: 1,
                url: 'https://solo5star.tistory.com',
              },
              {
                id: 2,
                url: 'https://solo5star.tistory.com',
              },
            ],
            childrenKeywords: [],
          },
          {
            keywordId: 5,
            name: 'var',
            order: 1,
            importance: 2,
            parentKeywordId: 2,
            description: 'var',
            doneQuizCount: 0,
            totalQuizCount: 5,
            recommendedPosts: [
              {
                id: 1,
                url: 'https://solo5star.tistory.com',
              },
              {
                id: 12,
                url: 'https://solo5star.tistory.com',
              },
              {
                id: 13,
                url: 'https://solo5star.tistory.com',
              },
              {
                id: 14,
                url: 'https://solo5star.tistory.com',
              },
            ],
            childrenKeywords: [],
          },
        ],
      },
    ],
  },
  // 세션 1 - React 키워드
  {
    keywordId: 6,
    name: 'React',
    order: 1,
    importance: 4,
    parentKeywordId: null,
    description: 'React입니다.',
    doneQuizCount: 3,
    totalQuizCount: 3,
    recommendedPosts: [],
    childrenKeywords: [
      {
        keywordId: 7,
        name: 'lifecycle',
        order: 1,
        importance: 3,
        parentKeywordId: 6,
        description: 'lifecycle 설명',
        doneQuizCount: 2,
        totalQuizCount: 4,
        recommendedPosts: [],
        childrenKeywords: [
          {
            keywordId: 8,
            name: 'mount',
            order: 1,
            importance: 1,
            parentKeywordId: 7,
            description: 'mount 설명',
            doneQuizCount: 1,
            totalQuizCount: 0,
            recommendedPosts: [],
            childrenKeywords: [],
          },
          {
            keywordId: 9,
            name: 'unmount',
            order: 1,
            importance: 1,
            parentKeywordId: 7,
            description: 'unmount 설명',
            doneQuizCount: 0,
            totalQuizCount: 0,
            recommendedPosts: [],
            childrenKeywords: [],
          },
          {
            keywordId: 10,
            name: 'update',
            order: 1,
            importance: 4,
            parentKeywordId: 7,
            description: 'update 설명',
            doneQuizCount: 0,
            totalQuizCount: 2,
            recommendedPosts: [],
            childrenKeywords: [],
          },
        ],
      },
      {
        keywordId: 14,
        name: 'hooks',
        order: 1,
        importance: 4,
        parentKeywordId: 6,
        description: 'hooks 설명',
        doneQuizCount: 0,
        totalQuizCount: 1,
        recommendedPosts: [],
        childrenKeywords: [
          {
            keywordId: 15,
            name: 'useState',
            order: 1,
            importance: 4,
            parentKeywordId: 14,
            description: 'useState 설명',
            doneQuizCount: 5,
            totalQuizCount: 5,
            recommendedPosts: [],
            childrenKeywords: [],
          },
          {
            keywordId: 16,
            name: 'useEffect',
            order: 1,
            importance: 4,
            parentKeywordId: 14,
            description: 'useEffect 설명',
            doneQuizCount: 4,
            totalQuizCount: 5,
            recommendedPosts: [],
            childrenKeywords: [],
          },
          {
            keywordId: 17,
            name: 'useMemo',
            order: 1,
            importance: 4,
            parentKeywordId: 14,
            description: 'useMemo 설명',
            doneQuizCount: 0,
            totalQuizCount: 2,
            recommendedPosts: [],
            childrenKeywords: [],
          },
        ],
      },
    ],
  },
  // 세션 2 - Test
  {
    keywordId: 11,
    name: 'Test',
    order: 1,
    importance: 3,
    parentKeywordId: null,
    description: 'Test입니다.',
    doneQuizCount: 0,
    totalQuizCount: 4,
    recommendedPosts: [],
    childrenKeywords: [
      {
        keywordId: 12,
        name: 'Jest',
        order: 1,
        importance: 1,
        parentKeywordId: 11,
        description: 'Jest 설명',
        doneQuizCount: 0,
        totalQuizCount: 2,
        recommendedPosts: [],
        childrenKeywords: [
          {
            keywordId: 13,
            name: 'React Testing Library',
            order: 1,
            importance: 1,
            parentKeywordId: 12,
            description: 'ReactTestingLibrary 설명',
            doneQuizCount: 0,
            totalQuizCount: 3,
            recommendedPosts: [],
            childrenKeywords: [],
          },
          {
            keywordId: 18,
            name: 'jest',
            order: 1,
            importance: 2,
            parentKeywordId: 12,
            description: 'jest 설명',
            doneQuizCount: 0,
            totalQuizCount: 6,
            recommendedPosts: [],
            childrenKeywords: [],
          },
        ],
      },
    ],
  },
];

export default {
  data: roadmapData,
  getKeywords() {
    return this.data;
  },
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
};
