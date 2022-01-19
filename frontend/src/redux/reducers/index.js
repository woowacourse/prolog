import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

import userReducer from './userReducer';
import postReducer from './postReducer';
import snackBarReducer from './snackBarReducer';

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user'],
};

const rootReducer = combineReducers({
  user: userReducer,
  post: postReducer,
  snackBar: snackBarReducer,
});

export default persistReducer(persistConfig, rootReducer);
