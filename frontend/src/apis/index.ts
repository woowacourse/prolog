import axios from 'axios';
import { BASE_URL } from '../configs/environment';
import LOCAL_STORAGE_KEY from '../constants/localStorage';

const getAccessToken = () => {
  const accessToken = localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
  if (!accessToken || accessToken === 'null') return null;

  return accessToken;
};

const accessToken = getAccessToken();

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    ...(accessToken ? { Authorization: `Bearer ${accessToken}` } : {}),
  },
});
