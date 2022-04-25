/** @jsxImportSource @emotion/react */

import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import { css } from '@emotion/react';
import { PATH } from './constants';
import { NavBar } from './components';
import pageRoutes from './routes';

export const MainContentStyle = css`
  max-width: 112rem;
  margin: 4rem auto;
  padding: 0 4rem 8rem;

  @media screen and (max-width: 420px) {
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
