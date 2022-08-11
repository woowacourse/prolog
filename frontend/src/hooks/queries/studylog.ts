import axios from 'axios';
import { useContext } from 'react';
import { useQuery } from 'react-query';
import { BASE_URL } from '../../configs/environment';
import { UserContext } from '../../contexts/UserProvider';
import { Studylog, StudyLogResponse } from '../../models/Studylogs';
import { requestGetStudylogs } from '../../service/requests';

const QUERY_KEY = {
  recentStudylogs: 'recentStudylogs',
  popularStudylogs: 'popularStudylogs',
};

export const useGetRecentStudylogsQuery = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  return useQuery<Studylog[]>([QUERY_KEY.recentStudylogs], async () => {
    const { data } = await requestGetStudylogs({
      query: { type: 'searchParams', data: 'size=3' },
      accessToken,
    }).then((res) => res.json());

    return data;
  });
};

export const useGetPopularStudylogsQuery = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  return useQuery<StudyLogResponse>('popularStudyLogs', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/studylogs/popular`,
      headers: accessToken && { Authorization: 'Bearer ' + accessToken },
    });

    return data;
  });
};
