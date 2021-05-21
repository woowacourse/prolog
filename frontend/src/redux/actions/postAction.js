export const CREATE_POST = 'post/CREATE_POST';
export const CREATE_POST_SUCCESS = 'post/CREATE_POST_SUCCESS';
export const CREATE_POST_FAILURE = 'post/CREATE_POST_FAILURE';

export const createPost = (posts, accessToken) => async (dispatch) => {
  dispatch({ type: CREATE_POST });

  try {
    const response = await fetch(`http://prolog.ap-northeast-2.elasticbeanstalk.com/posts`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        Authorization: `Bearer ${accessToken}`,
      },
      body: JSON.stringify(posts),
    });

    if (!response.ok) throw new Error(await response.text());

    dispatch({ type: CREATE_POST_SUCCESS });
  } catch (error) {
    console.error(error);
    dispatch({ type: CREATE_POST_FAILURE, payload: error });
  }
};
