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

  const axiosInstance = axios.create({
    baseURL: BASE_URL,
    headers: accessToken ? headers : {},
  });

  axiosInstance.interceptors.response.use(
    response => {
      // 성공적인 응답 처리
      return response;
    },
    error => {
      if (error.response && error.response.status === 400) {
        const { code } = error.response.data;
        if (code === 1002) {
          localStorage.removeItem('accessToken');
          window.location.href = '/';
          return Promise.resolve();
        }
      }

      return Promise.reject(error);
    }
  );

  return axiosInstance;
};
