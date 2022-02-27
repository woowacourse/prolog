import { BASE_URL } from '../configs/environment';

export const getPopularStudylogs = ({ accessToken }: { accessToken?: string }) => {
  if (accessToken) {
    return fetch(`${BASE_URL}/studylogs/most-popular`, {
      headers: { Authorization: 'Bearer ' + accessToken },
    });
  }

  return fetch(`${BASE_URL}/studylogs/most-popular`);
};
