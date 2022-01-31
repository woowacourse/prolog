import { BASE_URL } from '../configs/environment';

export const loginRequest = ({ code }) =>
  fetch(`${BASE_URL}/login/token`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: JSON.stringify({ code }),
  });

export const getUserProfileRequest = ({ accessToken }) =>
  fetch(`${BASE_URL}/members/me`, {
    headers: {
      'Content-Type': 'application/json; charset=UTF-8',
      Authorization: `Bearer ${accessToken}`,
    },
  });
