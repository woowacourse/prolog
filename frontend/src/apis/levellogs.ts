import { client } from '.';
import { CONFIRM_MESSAGE } from '../constants';
import { LevellogRequest } from '../models/Levellogs';

export const requestGetLevellogs = async () => {
  const { data } = await client.get(`/levellogs`);

  return data;
};

export const createNewLevellogRequest = (body: LevellogRequest) => client.post(`/levellogs`, body);

export const requestGetLevellog = async (id) => {
  const { data } = await client.get(`/levellogs/${id}`);

  return data;
};

export const requestDeleteLevellog = async (id) => {
  if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;

  client.delete(`/levellogs/${id}`);
};

export const requestPutLevellog = async (id, body) => client.put(`/levellogs/${id}`, body);
