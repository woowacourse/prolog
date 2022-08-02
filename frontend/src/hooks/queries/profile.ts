import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  requestDeleteScrap,
  requestEditProfile,
  requestGetMyScrap,
  requestGetProfile,
} from '../../service/requests';

const QUERY_KEY = {
  scrap: 'scrap',
  profile: 'profile',
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

export const useDeleteScrapMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(requestDeleteScrap, {
    onSuccess() {
      queryClient.invalidateQueries([QUERY_KEY.scrap]);
    },
  });
};

export const useGetProfileQuery = ({ username }, { onSuccess }) => {
  return useQuery(QUERY_KEY.profile, () => requestGetProfile(username).then((res) => res.json()), {
    onSuccess: (data) => {
      onSuccess(data);
    },
  });
};

export const usePutProfileMutation = ({ user, nickname, accessToken }, { onSuccess }) => {
  const queryClient = useQueryClient();

  return useMutation(
    () =>
      requestEditProfile(
        { username: user.username, nickname: nickname, imageUrl: user.imageUrl },
        accessToken
      ),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([QUERY_KEY.profile]);
        onSuccess();
      },
      onError: () => {
        alert('회원 정보 수정에 실패했습니다.');
      },
    }
  );
};
