/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { Redirect, Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import { NavBar } from './components';
import { PATH } from './constants';
import pageRoutes from './routes';
import mediaQuery from './utils/mediaQuery';

export const MainContentStyle = css`
  max-width: 112rem;
  margin: 4rem auto;
  padding: 0 4rem 8rem;

  ${mediaQuery.xs} {
    min-width: 280px;
    max-width: 420px;
    padding: 0 1rem;
    margin: 2rem auto;
  }
`;

const PageRouter = (): JSX.Element => {
  return (
    <Router>
      <NavBar />
      <Switch>
        {pageRoutes.map(({ path, render }) => (
          <Route exact path={path} render={render} key={path.toString()} />
        ))}
        <Redirect to={PATH.ROOT} />
      </Switch>
    </Router>
  );
};

export default PageRouter;
