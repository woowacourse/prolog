import { combineReducers } from 'redux';
import snackBarReducer from './snackBarReducer';

const rootReducer = combineReducers({
  snackBar: snackBarReducer,
});

export default rootReducer;
