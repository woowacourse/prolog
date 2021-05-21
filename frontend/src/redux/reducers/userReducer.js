import { LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE } from '../actions/userAction';

const initialState = {
  accessToken: {
    loading: false,
    data: '',
    error: null,
  },
};

const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        accessToken: {
          loading: true,
          data: '',
          error: null,
        },
      };
    case LOGIN_SUCCESS:
      return {
        ...state,
        accessToken: {
          loading: false,
          data: action.payload,
          error: null,
        },
      };
    case LOGIN_FAILURE:
      return {
        ...state,
        accessToken: {
          loading: false,
          data: '',
          error: action.payload,
        },
      };

    default:
      return state;
  }
};

export default userReducer;
