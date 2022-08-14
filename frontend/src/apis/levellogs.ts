import { client } from '.';
import { LevellogRequest } from '../models/Levellogs';

export const requestGetLevellogs = () => client.get(`/studylogs`);

export const createNewLevellogRequest = (body: LevellogRequest) => client.post(`/levellogs`, body);
