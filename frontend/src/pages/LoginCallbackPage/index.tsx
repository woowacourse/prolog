import { useContext, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';

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
      // history.goBack()을 이용하는 경우 로그인 허용 페이지로 돌아갈 가능성 있음.
      history.push(PATH.ROOT);
    };

    if (code) {
      login();
    }
  }, [code]);

  useEffect(() => {
    if (isLoggedIn) {
      history.push(PATH.ROOT);

      return;
    }
  }, []);

  return <></>;
};

export default LoginCallbackPage;
