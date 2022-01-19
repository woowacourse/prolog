import {
  LOGIN,
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  GET_PROFILE,
  GET_PROFILE_SUCCESS,
  GET_PROFILE_FAILURE,
  LOGOUT,
} from '../actions/userAction';
import ls from 'local-storage';
import { API } from '../../constants';

const initialState = {
  accessToken: {
    loading: false,
    data: ls.get(API.ACCESS_TOKEN),
    error: null,
  },

  profile: {
    loading: false,
    data: null,
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
          data: null,
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
          data: null,
          error: action.payload,
        },
      };

    case LOGOUT:
      return {
        accessToken: {
          loading: false,
          data: null,
          error: action.payload,
        },
        profile: {
          loading: false,
          data: null,
          error: action.payload,
        },
      };

    case GET_PROFILE:
      return {
        ...state,
        profile: {
          loading: true,
          data: null,
          error: action.payload,
        },
      };
    case GET_PROFILE_SUCCESS:
      return {
        ...state,
        profile: {
          loading: false,
          data: action.payload,
          error: null,
        },
      };
    case GET_PROFILE_FAILURE:
      return {
        ...state,
        profile: {
          loading: false,
          data: null,
          error: action.payload,
        },
      };

    default:
      return state;
  }
};

export default userReducer;
