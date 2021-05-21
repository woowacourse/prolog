import { CREATE_POST, CREATE_POST_SUCCESS, CREATE_POST_FAILURE } from '../actions/postAction';

const initialState = {
  posts: {
    loading: false,
    data: '',
    error: null,
  },
};

const postReducer = (state = initialState, action) => {
  switch (action.type) {
    case CREATE_POST:
      return {
        ...state,
        posts: {
          loading: true,
          data: state.posts.data,
          error: null,
        },
      };
    case CREATE_POST_SUCCESS:
      return {
        ...state,
        posts: {
          loading: false,
          data: state.posts.data,
          error: null,
        },
      };
    case CREATE_POST_FAILURE:
      return {
        ...state,
        posts: {
          loading: false,
          data: state.posts.data,
          error: action.payload,
        },
      };

    default:
      return state;
  }
};

export default postReducer;
