import { useLocation } from 'react-router-dom';
import { arrayMutation } from '../utils/arrayMutation';

const getValidQueries = <T extends string>(search: string, queryKeys: Readonly<T[]>) =>
  search
    .replace(/^\?/, '')
    .split('&')
    .reduce<Partial<Record<T, string>>>((queries, keyValue, _, search) => {
      const [key, value] = keyValue.split('=');
      const includes = arrayMutation(queryKeys, 'includes');

      if (queries.hasOwnProperty(key)) {
        throw new Error('Duplicated query');
      }

      return includes(key) ? { ...queries, [key]: value } : queries;
    }, Object.create(Object.prototype));

const useValidQueryString = <T extends string>(
  queryKeys: Readonly<T[]>
): Partial<Record<T, string>> => getValidQueries(useLocation().search, queryKeys);

export default useValidQueryString;
