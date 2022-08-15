import { useGetLevellogs } from '../queries/levellog';

export const useLevellogList = () => {
  const { data: levellogs, refetch: getAllLevellogs } = useGetLevellogs();

  return { levellogs, getAllLevellogs };
};
