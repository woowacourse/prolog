import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import styled from '@emotion/styled';

import useSnackBar from './hooks/useSnackBar';
import { PROFILE_PAGE_MENU, PATH } from './constants';
import { NavBar } from './components';
import {
  MainPage,
  NewPostPage,
  PostPage,
  LoginCallbackPage,
  EditPostPage,
  ProfilePage,
  ProfilePagePosts,
  ProfilePageReports,
  ProfilePageNewReport,
  ProfilePageEditReport,
  ProfilePageScraps,
} from './pages';

import GlobalStyles from './GlobalStyles';

const App = () => {
  const { isSnackBarOpen, SnackBar } = useSnackBar();

  return (
    <>
      <GlobalStyles />
      <Router>
        <NavBar />
        <Content>
          <Switch>
            <Route exact path={PATH.ROOT} component={MainPage} />
            <Route exact path={PATH.LOGIN_CALLBACK} component={LoginCallbackPage} />
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
              path={PATH.PROFILE_POSTS}
              render={() => (
                <ProfilePage menu={PROFILE_PAGE_MENU.POSTS}>
                  <ProfilePagePosts />
                </ProfilePage>
              )}
            />
            <Route
              exact
              path={`${PATH.PROFILE_REPORTS}`}
              render={() => (
                <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
                  <ProfilePageReports />
                </ProfilePage>
              )}
            />
            <Route
              exact
              path={`${PATH.PROFILE_NEW_REPORT}`}
              render={() => (
                <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
                  <ProfilePageNewReport />
                </ProfilePage>
              )}
            />
            <Route
              exact
              path={`${PATH.PROFILE_REPORTS}/:id/edit`}
              render={() => (
                <ProfilePage menu={PROFILE_PAGE_MENU.REPORTS}>
                  <ProfilePageEditReport />
                </ProfilePage>
              )}
            />
            <Route
              exact
              path={PATH.PROFILE_SCRAPS}
              render={() => (
                <ProfilePage menu={PROFILE_PAGE_MENU.SCRAPS}>
                  <ProfilePageScraps />
                </ProfilePage>
              )}
            />
            <Redirect to={PATH.ROOT} />
          </Switch>
        </Content>
      </Router>
      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

const Content = styled.div`
  max-width: 112rem;
  margin: 6rem auto;
  padding: 0 4rem;
`;

export default App;
