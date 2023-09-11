import { RoadmapResponse } from '../../models/Keywords';

const roadmapData: RoadmapResponse['data'] = [
  // 세션 1
  {
    keywordId: 1,
    name: 'JavaScript',
    order: 1,
    importance: 5,
    parentKeywordId: null,
    description: '동적 타이핑, 스크립트 언어입니다.',
    childrenKeywords: [
      {
        keywordId: 2,
        name: 'let, const, var',
        order: 1,
        importance: 5,
        parentKeywordId: 1,
        description: 'let, const, var',
        childrenKeywords: [
          {
            keywordId: 3,
            name: 'let',
            order: 1,
            importance: 5,
            parentKeywordId: 2,
            description: 'let',
            childrenKeywords: [],
          },
          {
            keywordId: 4,
            name: 'const',
            order: 1,
            importance: 5,
            parentKeywordId: 2,
            description: 'const',
            childrenKeywords: [],
          },
          {
            keywordId: 5,
            name: 'var',
            order: 1,
            importance: 5,
            parentKeywordId: 2,
            description: 'var',
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
    importance: 5,
    parentKeywordId: null,
    description: 'React입니다.',
    childrenKeywords: [
      {
        keywordId: 7,
        name: 'lifecycle',
        order: 1,
        importance: 5,
        parentKeywordId: 6,
        description: 'lifecycle 설명',
        childrenKeywords: [
          {
            keywordId: 8,
            name: 'mount',
            order: 1,
            importance: 5,
            parentKeywordId: 7,
            description: 'mount 설명',
            childrenKeywords: [],
          },
          {
            keywordId: 9,
            name: 'unmount',
            order: 1,
            importance: 5,
            parentKeywordId: 7,
            description: 'unmount 설명',
            childrenKeywords: [],
          },
          {
            keywordId: 10,
            name: 'update',
            order: 1,
            importance: 5,
            parentKeywordId: 7,
            description: 'update 설명',
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
    importance: 5,
    parentKeywordId: null,
    description: 'Test입니다.',
    childrenKeywords: [
      {
        keywordId: 12,
        name: 'Jest',
        order: 1,
        importance: 5,
        parentKeywordId: 11,
        description: 'Jest 설명',
        childrenKeywords: [
          {
            keywordId: 13,
            name: 'ReactTestingLibrary',
            order: 1,
            importance: 5,
            parentKeywordId: 12,
            description: 'ReactTestingLibrary 설명',
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
