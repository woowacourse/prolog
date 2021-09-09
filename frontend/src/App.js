import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { NavBar } from './components';
import styled from '@emotion/styled';
import { PROFILE_PAGE_MENU, PATH } from './constants';
import GlobalStyles from './GlobalStyles';
import {
  MainPage,
  NewPostPage,
  PostPage,
  LoginCallbackPage,
  EditPostPage,
  ProfilePage,
  ProfilePagePosts,
  ProfilePageAccount,
} from './pages';

const Content = styled.div`
  max-width: 112rem;
  margin: 6rem auto;
  padding: 0 4rem;
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
              <LoginCallbackPage />
            </Route>
            <Route exact path={PATH.NEW_POST} component={NewPostPage} />
            <Route exact path={`${PATH.POST}/:id`} component={PostPage} />
            <Route exact path={`${PATH.POST}/:id/edit`} component={EditPostPage} />
            <Route
              exact
              path={`${PATH.PROFILE}`}
              render={() => <ProfilePage menu={PROFILE_PAGE_MENU.OVERVIEW} />}
            />
            <Route
              exact
              path={`${PATH.PROFILE_POSTS}`}
              render={() => (
                <ProfilePage menu={PROFILE_PAGE_MENU.POSTS}>
                  <ProfilePagePosts />
                </ProfilePage>
              )}
            />
            <Route
              exact
              path={`${PATH.PROFILE_ACCOUNT}`}
              render={() => (
                <ProfilePage>
                  <ProfilePageAccount />
                </ProfilePage>
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
