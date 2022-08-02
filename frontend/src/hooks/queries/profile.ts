import { useMutation, useQuery, useQueryClient } from 'react-query';
import { requestDeleteScrap, requestGetMyScrap } from '../../service/requests';

const QUERY_KEY = {
  scrap: 'scrap',
};

export const useGetMyScrapQuery = ({ username, accessToken, postQueryParams }) => {
  return useQuery([QUERY_KEY.scrap, postQueryParams.page], () =>
    requestGetMyScrap({
      username,
      accessToken,
      postQueryParams,
    }).then((res) => res.json())
  );
};

export const useDeleteScrapMutation = (options) => {
  const queryClient = useQueryClient();

  return useMutation(requestDeleteScrap, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.scrap]);
    },
  });
};
