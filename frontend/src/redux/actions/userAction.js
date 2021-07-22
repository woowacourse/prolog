import qs from 'qs';
import ls from 'local-storage';
import { API } from '../../constants';

export const LOGIN = 'user/LOGIN';
export const LOGIN_SUCCESS = 'user/LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'user/LOGIN_FAILURE';

export const GET_PROFILE = 'user/GET_PROFILE';
export const GET_PROFILE_SUCCESS = 'user/GET_PROFILE_SUCCESS';
export const GET_PROFILE_FAILURE = 'user/GET_PROFILE_FAILURE';

const BASE_URL = process.env.REACT_APP_API_URL;

export const login = () => async (dispatch) => {
  dispatch({ type: LOGIN });

  const { code } = qs.parse(window.location.search, {
    ignoreQueryPrefix: true,
  });

  if (!code) return;

  try {
    const response = await fetch(`${BASE_URL}/login/token`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: JSON.stringify({ code }),
    });

    if (!response.ok) throw new Error(await response.text());

    const { accessToken } = await response.json();

    ls.set(API.ACCESS_TOKEN, accessToken);
    dispatch({ type: LOGIN_SUCCESS, payload: accessToken });
  } catch (error) {
    const errorResponse = JSON.parse(error.message);

    console.error(errorResponse);
    dispatch({ type: LOGIN_FAILURE, payload: errorResponse });
  }
};

export const getProfile = (accessToken) => async (dispatch) => {
  dispatch({ type: GET_PROFILE });
  const accessToken = ls.get(API.ACCESS_TOKEN);

  try {
    const response = await fetch(`${BASE_URL}/members/me`, {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
    });

    const status = response.status;

    if (status === API.STATUS.EXPIRED_ACCESS_TOKEN) {
      throw new Error(await response.text());
    }

    const profile = await response.json();

    dispatch({ type: GET_PROFILE_SUCCESS, payload: profile });
  } catch (error) {
    const errorResponse = JSON.parse(error.message);

    console.error(errorResponse);
    dispatch({ type: GET_PROFILE_FAILURE, payload: errorResponse });
  }
};
