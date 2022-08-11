import { useMutation } from 'react-query';
import { client } from '../../apis';
import LOCAL_STORAGE_KEY from '../../constants/localStorage';
import { loginRequest } from '../../service/requests';

export const useLogin = ({ onSuccess }) =>
  useMutation<any, unknown, { code: string }>(
    async ({ code }) => {
      const res = await loginRequest({ code });

      return await res.json();
    },
    {
      onSuccess: ({ accessToken }) => {
        localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(accessToken));
        client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

        onSuccess(accessToken);
      },
      onError: (error) => {
        if (error instanceof Error) {
          alert(error.message);
        }
      },
    }
  );
