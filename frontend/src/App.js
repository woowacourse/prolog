import { useState } from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { NavBar } from './components';
import styled from '@emotion/styled';
import { MYPAGE_MENU, PATH } from './constants';
import GlobalStyles from './GlobalStyles';
import {
  MainPage,
  NewPostPage,
  PostPage,
  LoginCallbackPage,
  EditPostPage,
  MyPage,
  MyPagePosts,
  MyPageAccount,
} from './pages';

const Content = styled.div`
  max-width: 112rem;
  margin: 6rem auto;
  padding: 0 4rem;
`;

const App = () => {
  // const [selectedMenu, setSelectedMenu] = useState('overview');

  return (
    <>
      <GlobalStyles />
      <Router>
        <NavBar />
        <Content>
          <Switch>
            <Route exact path={PATH.ROOT} component={MainPage} />
            <Route exact path={PATH.LOGIN_CALLBACK}>
              <LoginCallbackPage />
            </Route>
            <Route exact path={PATH.NEW_POST} component={NewPostPage} />
            <Route exact path={`${PATH.POST}/:id`} component={PostPage} />
            <Route exact path={`${PATH.POST}/:id/edit`} component={EditPostPage} />
            <Route
              exact
              path={`${PATH.MYPAGE}`}
              render={() => <MyPage menu={MYPAGE_MENU.OVERVIEW} />}
            />
            <Route
              exact
              path={`${PATH.MYPAGE_POSTS}`}
              render={() => (
                <MyPage menu={MYPAGE_MENU.POSTS}>
                  <MyPagePosts />
                </MyPage>
              )}
            />
            <Route
              exact
              path={`${PATH.MYPAGE_ACCOUNT}`}
              render={() => (
                <MyPage>
                  <MyPageAccount />
                </MyPage>
              )}
            />
            <Redirect to={PATH.ROOT} />
          </Switch>
        </Content>
      </Router>
    </>
  );
};

export default App;
