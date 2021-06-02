import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { NavBar } from './components';
import styled from '@emotion/styled';
import { PATH } from './constants';
import GlobalStyles from './GlobalStyles';
import { MainPage, NewPostPage, PostPage } from './pages';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { login } from './redux/actions/userAction';

import LoginCallback from './pages/LoginCallback';

const Content = styled.div`
  max-width: 128rem;
  margin: 6rem auto;
`;

const App = () => {
  return (
    <>
      <GlobalStyles />
      <Router>
        <NavBar />
        <Content>
          <Switch>
            <Route exact path={PATH.ROOT} component={MainPage} />
            <Route exact path={PATH.LOGIN_CALLBACK}>
              <LoginCallback />
            </Route>
            <Route exact path={PATH.NEW_POST} component={NewPostPage} />
            <Route exact path={`${PATH.POST}/:id`} component={PostPage} />
            <Redirect to={PATH.ROOT} />
          </Switch>
        </Content>
      </Router>
    </>
  );
};

export default App;
