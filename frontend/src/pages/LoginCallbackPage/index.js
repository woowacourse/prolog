import { useContext, useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import { UserContext } from '../../contexts/UserProvider';

// TODO: 로그인 실패 시 에러처리 하기, 로딩 상태 관리
const LoginCallbackPage = () => {
  const history = useHistory();
  const code = new URLSearchParams(history.location.search).get('code');

  const { user, onLogin } = useContext(UserContext);
  const isLoggedIn = user.isLoggedIn;

  useEffect(() => {
    const login = async () => {
      await onLogin({ code });
      history.goBack();
    };

    if (code) {
      login();
    }
  }, [code]);

  useEffect(() => {
    if (isLoggedIn) {
      history.goBack();

      return;
    }
  }, []);

  return <></>;
};

export default LoginCallbackPage;
