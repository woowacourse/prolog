import axios from 'axios';
import { BASE_URL } from '../configs/environment';
import LOCAL_STORAGE_KEY from '../constants/localStorage';

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN)}`,
  },
});
