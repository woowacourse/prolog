import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';

import { login } from '../../redux/actions/userAction';

const LoginCallbackPage = () => {
  const dispatch = useDispatch();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const history = useHistory();

  useEffect(() => {
    dispatch(login());
  }, [dispatch]);

  useEffect(() => {
    if (accessToken) {
      history.goBack();
    } else {
      console.error('get accessToken failed');
      history.push('/');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);

  return <></>;
};

export default LoginCallbackPage;
