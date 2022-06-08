import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import 'antd/dist/antd.css';

import App from './App';
import store from './redux/store';
import UserProvider from './contexts/UserProvider';
import { isDev, isLocal } from './configs/environment';

if (isDev || isLocal) {
  const { worker } = require('./mocks/browser');
  worker.start();
}

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
