import { client } from '.';
import { LevellogRequest } from '../models/Levellogs';

export const requestGetLevellogs = async (currPage: number) => {
  const params = currPage !== 1 ? `?page=${currPage}` : '';

  const { data } = await client.get(`/levellogs${params}`);

  return data;
};

export const createNewLevellogRequest = (body: LevellogRequest) => client.post(`/levellogs`, body);

export const requestGetLevellog = async (id) => {
  const { data } = await client.get(`/levellogs/${id}`);

  return data;
};

export const requestDeleteLevellog = async (id) => {
  client.delete(`/levellogs/${id}`);
};

export const requestEditLevellog = (id, body: LevellogRequest) =>
  client.put(`/levellogs/${id}`, body);
