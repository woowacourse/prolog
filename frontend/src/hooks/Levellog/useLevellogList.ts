import { useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { UserContext } from '../../contexts/UserProvider';
import { useGetLevellogs } from '../queries/levellog';

export const useLevellogList = () => {
  const history = useHistory();
  const { user } = useContext(UserContext);

  const currPage = Number(history.location.search.replace('?page=', '')) || 1;
  const { isLoggedIn } = user;

  const { data: levellogs, refetch: getAllLevellogs, isLoading } = useGetLevellogs(currPage);

  const onChangeCurrentPage = (page) => {
    const params = page !== 1 ? `?page=${page}` : '';
    const url = `/levellogs${params}`;

    history.push(url);
    window.scrollTo({ left: 0, top: 0 });
  };

  return { levellogs, getAllLevellogs, isLoading, currPage, onChangeCurrentPage, isLoggedIn };
};
