import { createAxiosInstance } from '../utils/axiosInstance';
import { LevellogRequest } from '../models/Levellogs';

const customAxios = createAxiosInstance();

export const requestGetLevellogs = async (currPage: number) => {
  const params = currPage !== 1 ? `?page=${currPage}` : '';

  const { data } = await customAxios.get(`/levellogs${params}`);

  return data;
};

export const createNewLevellogRequest = (body: LevellogRequest) =>
  customAxios.post(`/levellogs`, body);

export const requestGetLevellog = async (id) => {
  const { data } = await customAxios.get(`/levellogs/${id}`);

  return data;
};

export const requestDeleteLevellog = async (id) => {
  customAxios.delete(`/levellogs/${id}`);
};

export const requestEditLevellog = (id, body: LevellogRequest) =>
  customAxios.put(`/levellogs/${id}`, body);
