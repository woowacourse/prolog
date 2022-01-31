import { createContext, useState } from 'react';

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
  const [state, setState] = useState(DEFAULT_USER);

  const userProfileRequest = useRequest(
    DEFAULT_USER,
    () => getUserProfileRequest({ accessToken: state.accessToken }),
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
    () => {
      localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
      setState(DEFAULT_USER);
    }
  );

  const { mutate: onLogin } = useMutation(loginRequest, {
    onSuccess: ({ accessToken }) => {
      localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(accessToken));
    },
  });

  const onLogout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    setState(DEFAULT_USER);
  };

  useState(() => {
    const accessToken = getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);

    if (accessToken) {
      setState({ ...state, accessToken });
    }
  }, []);

  return (
    <UserContext.Provider value={{ user: state, onLogin, onLogout }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
