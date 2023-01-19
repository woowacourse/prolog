import { useQuery } from 'react-query';
import {
  getKeyword,
  getTopKeywordList,
  getChildKeywordList,
  getQuizListByKeyword,
} from '../../apis/keywords';

const QUERY_KEY = {
  keyword: 'keyword',
  topKeywordList: 'topKeywordList',
  childKeywordList: 'childKeywordList',
  quizListByKeyword: 'quizListByKeyword',
};

export const useGetKeyword = ({
  sessionId,
  keywordId,
}: {
  sessionId: number;
  keywordId: number;
}) => {
  const { data } = useQuery([QUERY_KEY.keyword, sessionId, keywordId], () =>
    getKeyword({
      sessionId,
      keywordId,
    })
  );

  return {
    keyword: data,
  };
};

/** 5. 세션별 키워드 목록 조회. 1 depth */
export const useGetTopKeywordList = (sessionId: number) => {
  const { data } = useQuery([QUERY_KEY.topKeywordList, sessionId], () =>
    getTopKeywordList(sessionId)
  );

  return {
    topKeywordList: data?.data,
  };
};

export const useGetChildrenKeywordList = ({
  sessionId,
  keywordId,
}: {
  sessionId: number;
  keywordId: number;
}) => {
  const { data, refetch } = useQuery([QUERY_KEY.childKeywordList, sessionId, keywordId], () =>
    getChildKeywordList({
      sessionId,
      keywordId,
    })
  );

  return {
    childrenKeywordList: data?.childrenKeywords,
    refetchChildrenKeywordList: refetch,
  };
};

export const useGetQuizListByKeyword = ({
  sessionId,
  keywordId,
}: {
  sessionId: number;
  keywordId: number;
}) => {
  const { data } = useQuery([QUERY_KEY.quizListByKeyword, sessionId, keywordId], () =>
    getQuizListByKeyword({
      sessionId,
      keywordId,
    })
  );

  return {
    quizList: data?.data,
  };
};
