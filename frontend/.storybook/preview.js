import { configure, addDecorator } from '@storybook/react';
import { BrowserRouter as Router } from 'react-router-dom';
import * as React from 'react';
import GlobalStyles from '../src/GlobalStyles';

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

addDecorator((style) => (
  <>
    <GlobalStyles />
    <Router>{style()}</Router>
  </>
));

configure(require.context('../src', true, /\.stories\.js?$/), module);
