import { createAxiosInstance } from '../utils/axiosInstance';
import { LevellogRequest } from '../models/Levellogs';

const instanceWithoutToken = createAxiosInstance();

export const requestGetLevellogs = async (currPage: number) => {
  const params = currPage !== 1 ? `?page=${currPage}` : '';

  const { data } = await instanceWithoutToken.get(`/levellogs${params}`);

  return data;
};

export const createNewLevellogRequest = (body: LevellogRequest) =>
  instanceWithoutToken.post(`/levellogs`, body);

export const requestGetLevellog = async (id) => {
  const { data } = await instanceWithoutToken.get(`/levellogs/${id}`);

  return data;
};

export const requestDeleteLevellog = async (id) => {
  instanceWithoutToken.delete(`/levellogs/${id}`);
};

export const requestEditLevellog = (id, body: LevellogRequest) =>
  instanceWithoutToken.put(`/levellogs/${id}`, body);
