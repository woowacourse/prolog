import { useQuery } from 'react-query';
import { getCurriculums } from '../../apis/curriculum';

const QUERY_KEY = {
  curriculum: 'curriculum',
};

export const useGetCurriculums = () => {
  const { data, isLoading } = useQuery([QUERY_KEY.curriculum], () => getCurriculums());

  return {
    curriculums: data?.data,
    isLoading,
  };
};
