import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { PATH } from './constants';
import { MainPage, NewPostPage, PostPage } from './pages';

const App = () => {
  return (
    <>
      <Router>
        <Switch>
          <Route exact path={PATH.ROOT} component={MainPage} />
          <Route exact path={PATH.POST} component={PostPage} />
          <Route exact path={PATH.NEW_POST} component={NewPostPage} />
          <Redirect to={PATH.ROOT} />
        </Switch>
      </Router>
    </>
  );
};

export default App;
