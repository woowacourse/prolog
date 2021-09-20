import { CLOSE_SNACKBAR, OPEN_SNACKBAR } from '../actions/snackBarAction';

const initialState = {
  isSnackBarOpen: false,
  message: '',
};

const snackBarReducer = (state = initialState, action) => {
  switch (action.type) {
    case OPEN_SNACKBAR:
      return {
        ...state,
        isSnackBarOpen: true,
        message: action.payload,
      };
    case CLOSE_SNACKBAR:
      return {
        ...state,
        isSnackBarOpen: false,
        message: 'action.payload',
      };

    default:
      return state;
  }
};

export default snackBarReducer;
