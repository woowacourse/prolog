import { client } from '.';
import { FilterResponse } from '../models/filter';
import { Author } from '../models/Studylogs';

export const getMembersForFilter = async (): Promise<Author[]> => {
  const {
    data: { members },
  } = await client.get<FilterResponse>(`/filters`);

  return members;
};
