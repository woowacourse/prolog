import { useQuery } from 'react-query';
import { getComments } from '../../apis/comments';

export const useFetchComments = (studylogId: number) =>
  useQuery(['comments', studylogId], () => getComments(studylogId));
