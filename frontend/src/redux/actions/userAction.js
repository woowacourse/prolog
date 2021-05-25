import qs from 'qs';
import ls from 'local-storage';

export const LOGIN = 'user/LOGIN';
export const LOGIN_SUCCESS = 'user/LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'user/LOGIN_FAILURE';

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

    ls.set('login', accessToken);
    dispatch({ type: LOGIN_SUCCESS, payload: accessToken });
  } catch (error) {
    console.error(error);
    dispatch({ type: LOGIN_FAILURE, payload: error });
  }
};
