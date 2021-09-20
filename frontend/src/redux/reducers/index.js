import { combineReducers } from 'redux';
import userReducer from './userReducer';
import postReducer from './postReducer';
import snackBarReducer from './snackBarReducer';

const rootReducer = combineReducers({
  user: userReducer,
  post: postReducer,
  snackBar: snackBarReducer,
});

export default rootReducer;
