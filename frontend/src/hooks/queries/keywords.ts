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
  return useQuery([QUERY_KEY.keyword], () =>
    getKeyword({
      sessionId,
      keywordId,
    })
  );
};

export const useGetTopKeywordList = (sessionId: number) => {
  const { data } = useQuery([QUERY_KEY.topKeywordList], () => getTopKeywordList(sessionId));

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
  const { data, refetch } = useQuery([QUERY_KEY.childKeywordList], () =>
    getChildKeywordList({
      sessionId,
      keywordId,
    })
  );

  return {
    childrenKeywordList: data?.data,
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
  const { data } = useQuery([QUERY_KEY.quizListByKeyword], () =>
    getQuizListByKeyword({
      sessionId,
      keywordId,
    })
  );

  return {
    quizList: data?.data,
  };
};
