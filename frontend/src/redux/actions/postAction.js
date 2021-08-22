export const CREATE_POST = 'post/CREATE_POST';
export const CREATE_POST_SUCCESS = 'post/CREATE_POST_SUCCESS';
export const CREATE_POST_FAILURE = 'post/CREATE_POST_FAILURE';

const BASE_URL = process.env.REACT_APP_API_URL;

export const createPost = (posts, accessToken) => async (dispatch) => {
  dispatch({ type: CREATE_POST });

  try {
    const response = await fetch(`${BASE_URL}/posts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
      body: JSON.stringify(posts),
    });

    if (!response.ok) throw new Error(await response.text());

    dispatch({ type: CREATE_POST_SUCCESS });
    return true;
  } catch (error) {
    const errorResponse = JSON.parse(error.message);

    console.error(errorResponse);
    alert(errorResponse.message);
    // dispatch({ type: CREATE_POST_FAILURE, payload: errorResponse });
  }
};
