import axios from 'axios';
import { BASE_URL } from '../configs/environment';
import LOCAL_STORAGE_KEY from '../constants/localStorage';

const accessToken = localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);

const headers = {
  Authorization: `Bearer ${accessToken}`,
};

export const client = axios.create({
  baseURL: BASE_URL,
  headers: accessToken ? headers : {},
});
