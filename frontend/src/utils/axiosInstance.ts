import axios from 'axios';
import { BASE_URL } from '../configs/environment';

interface AxiosInstanceProps {
  accessToken?: string;
}

export const createAxiosInstance = ({ accessToken }: AxiosInstanceProps = {}) => {
  const headers = {
    'Content-Type': 'application/json; charset=UTF-8',
  };

  if (accessToken) {
    headers['Authorization'] = `Bearer ${accessToken}`;
  }

  return axios.create({
    baseURL: BASE_URL,
    headers: accessToken ? headers : {},
  });
};
