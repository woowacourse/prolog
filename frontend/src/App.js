import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
/** @jsxImportSource @emotion/react */
/* 
  - React.createElement 대신 jsx를 jsx라는 함수로 변환 (babel)
  - @emotion/react 적용을 위해 필요
*/
import { css } from '@emotion/react';

import { PATH } from './constants';
import { NavBar } from './components';
import pageRoutes from './routes';

import useSnackBar from './hooks/useSnackBar';
import GlobalStyles from './GlobalStyles';

const ContentStyle = css`
  max-width: 112rem;
  margin: 6rem auto;
  padding: 0 4rem;
`;

const App = () => {
  const { isSnackBarOpen, SnackBar } = useSnackBar();

  return (
    <>
      <GlobalStyles />
      <Router>
        <NavBar />
        <div css={ContentStyle}>
          <Switch>
            {pageRoutes.map(({ path, render }) => (
              <Route exact path={path} render={render} key={path.toString} />
            ))}
            <Redirect to={PATH.ROOT} />
          </Switch>
        </div>
      </Router>
      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

export default App;
