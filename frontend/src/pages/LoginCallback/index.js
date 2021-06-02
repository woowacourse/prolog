import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router';

import { login } from '../../redux/actions/userAction';

const LoginCallback = () => {
  const dispatch = useDispatch();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const history = useHistory();

  useEffect(() => {
    dispatch(login());
  }, [dispatch]);

  useEffect(() => {
    if (accessToken) {
      console.log('login 성공');
      // history.goBack();
    } else {
      console.error('get accessToken failed');
      // history.push('/');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);

  return <>Login Callback Page</>;
};

export default LoginCallback;
