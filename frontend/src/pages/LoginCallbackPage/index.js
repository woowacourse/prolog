import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';

import { login } from '../../redux/actions/userAction';

const LoginCallbackPage = () => {
  const dispatch = useDispatch();
  const { loading, data: accessToken, error } = useSelector((state) => state.user.accessToken);

  const history = useHistory();

  useEffect(() => {
    dispatch(login());
  }, [dispatch]);

  useEffect(() => {
    if (loading) {
      return;
    }

    if (error) {
      console.error(error);
      history.goBack();

      return;
    }

    if (accessToken) {
      history.goBack();

      return;
    }
  }, [loading, error]);

  return <></>;
};

export default LoginCallbackPage;
