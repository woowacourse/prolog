import { createContext, useEffect, useState } from 'react';

import LOCAL_STORAGE_KEY from '../constants/localStorage';
import useMutation from '../hooks/useMutation';
import useRequest from '../hooks/useRequest';
import { getUserProfileRequest, loginRequest } from '../service/userRequests';
import { getLocalStorageItem } from '../utils/localStorage';

const DEFAULT_USER = {
  userId: null,
  username: null,
  nickname: null,
  role: null,
  imageUrl: null,
  accessToken: getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN),
  isLoggedIn: false,
};

export const UserContext = createContext(DEFAULT_USER);

const UserProvider = ({ children }) => {
  const [state, setState] = useState(DEFAULT_USER);

  const userProfileRequest = useRequest(
    DEFAULT_USER,
    () => getUserProfileRequest({ accessToken: state.accessToken }),
    ({ id: userId, username, nickname, role, imageUrl }) => {
      setState({ ...state, userId, username, nickname, role, imageUrl, isLoggedIn: true });
    },
    () => {
      setState(DEFAULT_USER);
    }
  );

  const { mutate: onLogin } = useMutation(loginRequest, {
    onSuccess: ({ accessToken }) => {
      localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(accessToken));
      setState({ ...state, accessToken });
    },
  });

  const onLogout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    setState(DEFAULT_USER);
  };

  useEffect(() => {
    console.log(state);
  }, [state]);

  return (
    <UserContext.Provider value={{ user: state, onLogin, onLogout }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
