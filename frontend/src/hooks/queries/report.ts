import { useQuery } from 'react-query';
import { requestGetMatchedStudylogs } from '../../service/requests';

const QUERY_KEY = {
  matchedStudylogs: 'matchedStudylogs',
};

export const useGetMatchedStudylogs = ({ accessToken, startDate, endDate }) => {
  return useQuery(
    [QUERY_KEY.matchedStudylogs],
    () => requestGetMatchedStudylogs({ accessToken, startDate, endDate }).then((res) => res.json()),
    { enabled: false }
  );
};
