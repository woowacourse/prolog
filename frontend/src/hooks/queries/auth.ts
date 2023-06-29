import { useMutation } from 'react-query';
import { loginRequest } from '../../service/requests';

export const useLogin = ({ onSuccess }) =>
  useMutation<any, unknown, { code: string }>(
    async ({ code }) => {
      const response = await loginRequest({ code });

      return response.data;
    },
    {
      onSuccess: ({ accessToken }) => {
        onSuccess(accessToken);
      },
      onError: (error) => {
        if (error instanceof Error) {
          alert(error.message);
        }
      },
    }
  );
