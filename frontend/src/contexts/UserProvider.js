import { createContext, useState, useEffect } from 'react';
import { client } from '../apis';

import LOCAL_STORAGE_KEY from '../constants/localStorage';
import useMutation from '../hooks/useMutation';
import useRequest from '../hooks/useRequest';
import { getUserProfileRequest, loginRequest } from '../service/requests';
import { getLocalStorageItem } from '../utils/localStorage';

const DEFAULT_USER = {
  userId: null,
  username: null,
  nickname: null,
  role: null,
  imageUrl: null,
  accessToken: null,
  isLoggedIn: false,
};

export const UserContext = createContext(DEFAULT_USER);

const UserProvider = ({ children }) => {
  const [state, setState] = useState({
    ...DEFAULT_USER,
    accessToken: getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN),
  });

  useEffect(() => {
    userProfileRequest.fetchData();
  }, [state.accessToken]);

  const userProfileRequest = useRequest(
    state,
    () => {
      if (!state.accessToken) {
        return;
      }

      return getUserProfileRequest({ accessToken: state.accessToken });
    },
    ({ id: userId, username, nickname, role, imageUrl }) => {
      setState((prev) => ({
        ...prev,
        userId,
        username,
        nickname,
        role,
        imageUrl,
        isLoggedIn: true,
      }));
    },
    onLogout
  );

  const { mutate: onLogin } = useMutation(loginRequest, {
    onSuccess: ({ accessToken }) => {
      localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(accessToken));
      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
      setState((prev) => ({ ...prev, accessToken }));
    },
    onError: (error) => {
      alert(error.message);
    },
  });

  function onLogout() {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    client.defaults.headers['Authorization'] = '';
    setState(DEFAULT_USER);
  }

  return (
    <UserContext.Provider value={{ user: state, onLogin, onLogout }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
