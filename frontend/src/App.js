import {
  QueryClient,
  QueryClientProvider,
} from 'react-query'
import { ReactQueryDevtools } from 'react-query/devtools'

import useSnackBar from './hooks/useSnackBar';
import GlobalStyles from './GlobalStyles';
import PageRouter from './PageRouter';

const queryClient = new QueryClient()

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
