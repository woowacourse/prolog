import { useQuery } from 'react-query';
import { requestGetMatchedStudylogs } from '../../service/requests';

const QUERY_KEY = {
  matchedStudylogs: 'matchedStudylogs',
};

export const useGetMatchedStudylogs = ({ accessToken, startDate, endDate }) => {
  const fetchMatchedStudylogs = async () => {
    const res = await requestGetMatchedStudylogs({ accessToken, startDate, endDate });
    return res.data;
  };

  return useQuery([QUERY_KEY.matchedStudylogs], fetchMatchedStudylogs, { enabled: false });
};
