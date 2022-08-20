import { useHistory } from 'react-router-dom';
import { useGetLevellogs } from '../queries/levellog';

export const useLevellogList = () => {
  const history = useHistory();
  const currPage = Number(history.location.search.replace('?page=', '')) || 1;

  const { data: levellogs, refetch: getAllLevellogs, isLoading } = useGetLevellogs(currPage);

  const onChangeCurrentPage = (page) => {
    const params = page !== 1 ? `?page=${page}` : '';
    const url = `/levellogs${params}`;

    history.push(url);
  };

  return { levellogs, getAllLevellogs, isLoading, currPage, onChangeCurrentPage };
};
