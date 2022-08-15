import { client } from '.';
import { LevellogRequest } from '../models/Levellogs';

export const requestGetLevellogs = async () => {
  const { data } = await client.get(`/levellogs`);

  return data;
};

export const createNewLevellogRequest = (body: LevellogRequest) => client.post(`/levellogs`, body);
