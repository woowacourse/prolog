import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { NavBar } from './components';
import styled from '@emotion/styled';
import { PATH } from './constants';
import GlobalStyles from './GlobalStyles';
import { MainPage, NewPostPage, PostPage } from './pages';

const Content = styled.div`
  max-width: 128rem;
  margin: 10rem auto;
`;

const App = () => {
  return (
    <>
      <GlobalStyles />
      <Router>
        <NavBar />
        <Switch>
          <Content>
            <Route exact path={PATH.ROOT} component={MainPage} />
            <Route exact path={PATH.POST} component={PostPage} />
            <Route exact path={PATH.NEW_POST} component={NewPostPage} />
            <Redirect to={PATH.ROOT} />
          </Content>
        </Switch>
      </Router>
    </>
  );
};

export default App;
