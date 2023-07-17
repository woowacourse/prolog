import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  requestDeleteScrap,
  requestEditProfile,
  requestGetProfile,
  requestEditProfileIntroduction,
  requestGetMyScrap,
  requestGetProfileIntroduction,
} from '../../service/requests';

const QUERY_KEY = {
  scrap: 'scrap',
  profile: 'profile',
  introduction: 'introduction',
};

export const useGetMyScrapQuery = ({ username, accessToken, postQueryParams }) => {
  const fetchMyScrap = async () => {
    const res = await requestGetMyScrap({
      username,
      accessToken,
      postQueryParams,
    });
    return res.data;
  };

  return useQuery([QUERY_KEY.scrap, postQueryParams.page], fetchMyScrap);
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
  const fetchProfile = async () => {
    const res = await requestGetProfile(username);
    return res.data;
  };

  return useQuery(QUERY_KEY.profile, fetchProfile, {
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
export const usePutProfileIntroductionMutation = (
  { username, editorContentRef, accessToken },
  options
) => {
  return useMutation(
    () =>
      requestEditProfileIntroduction(
        username,
        editorContentRef.current.getInstance().getMarkdown(),
        accessToken
      ),
    options
  );
};

export const useGetProfileIntroduction = ({ username }) => {
  const fetchProfileIntroduction = async () => {
    const res = await requestGetProfileIntroduction(username);
    return res.data;
  };

  return useQuery(QUERY_KEY.introduction, fetchProfileIntroduction);
};
