import { useQuery } from 'react-query';
import { getKeyword, getQuizListByKeyword, getRoadmap } from '../../apis/keywords';
import type { RoadmapRequest } from '../../models/Keywords';

const QUERY_KEY = {
  keyword: 'keyword',
  roadmap: 'roadmap',
  childKeywordList: 'childKeywordList',
  quizListByKeyword: 'quizListByKeyword',
};

export const useRoadmap = ({ curriculumId }: RoadmapRequest) => {
  return useQuery({
    queryKey: [QUERY_KEY.roadmap, curriculumId],
    queryFn: () => getRoadmap({ curriculumId }),
  });
};

export const useGetKeyword = ({ keywordId }: { keywordId: number }) => {
  const { data } = useQuery([QUERY_KEY.keyword, keywordId], () => getKeyword({ keywordId }));

  return {
    keyword: data,
  };
};

export const useGetQuizListByKeyword = ({ keywordId }: { keywordId: number }) => {
  const { data } = useQuery([QUERY_KEY.quizListByKeyword, keywordId], () =>
    getQuizListByKeyword({ keywordId })
  );

  return {
    quizList: data?.data,
  };
};
