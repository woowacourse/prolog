const LEVELLOG_LIST_LENGTH = 100;
export const LEVELLOG_ITEM_PER_PAGE = 10;

const levellogs = Array.from({ length: LEVELLOG_LIST_LENGTH }, (_, idx) => idx + 1).map((idx) => ({
  id: idx,
  title: `자바를 하면서 궁금했던 것들 ${idx}번째 ㅎㅎ`,
  content: '자바는 왜 이름이 자바일까?',
  author: {
    id: 1,
    username: 'kwannee',
    nickname: '후이',
    role: 'CREW',
    imageUrl: 'https://avatars.githubusercontent.com/u/41886825?&v=4',
  },
  levelLogs: [
    {
      id: 1,
      question: '레벨 인터뷰 예상 질문1',
      answer: '질문에 대한 답변1',
    },
    {
      id: 2,
      question: '레벨 인터뷰 예상 질문2',
      answer: '질문에 대한 답변2',
    },
    {
      id: 3,
      question: '레벨 인터뷰 예상 질문3',
      answer: '질문에 대한 답변3',
    },
  ],
  createdAt: '2022-07-10T23:06:45.395224',
  updatedAt: '2022-07-10T23:06:45.395224',
}));

levellogs.sort((log1, log2) => log2.id - log1.id);

const dummyLevellogsData = {
  data: levellogs,
  totalSize: LEVELLOG_LIST_LENGTH,
  totalPage: LEVELLOG_LIST_LENGTH / LEVELLOG_ITEM_PER_PAGE,
  currPage: 1,
};

export default dummyLevellogsData;
