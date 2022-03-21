import { QueryClient, QueryClientProvider } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import ReactGA from 'react-ga';
const TRACKING_ID = process.env.REACT_APP_GA_TRACKING_ID;
ReactGA.initialize(TRACKING_ID);

import useSnackBar from './hooks/useSnackBar';
import GlobalStyles from './GlobalStyles';
import PageRouter from './PageRouter';

const queryClient = new QueryClient();

const App = () => {
  const { isSnackBarOpen, SnackBar } = useSnackBar();

  return (
    <>
      <GlobalStyles />
      <QueryClientProvider client={queryClient}>
        <PageRouter />
        <ReactQueryDevtools initialIsOpen={false} />
      </QueryClientProvider>
      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

export default App;
