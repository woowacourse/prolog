import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import 'antd/dist/antd.css';

import App from './App';
import store from './redux/store';
import UserProvider from './contexts/UserProvider';

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <UserProvider>
        <App />
      </UserProvider>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);
