import { useEffect } from 'react';
import ReactDOM from 'react-dom';
import { useDispatch, useSelector } from 'react-redux';
import { default as SnackBarComponent } from '../components/@shared/SnackBar/SnackBar';
import { CLOSE_SNACKBAR, OPEN_SNACKBAR } from '../redux/actions/snackBarAction';

const useSnackBar = () => {
  const dispatch = useDispatch();
  const { isSnackBarOpen, message } = useSelector((state) => state.snackBar);

  const SnackbarPortal = ({ children }) =>
    ReactDOM.createPortal(children, document.getElementById('snackbar'));

  const SnackBar = () => (
    <SnackbarPortal>
      <SnackBarComponent>{message}</SnackBarComponent>
    </SnackbarPortal>
  );

  const openSnackBar = (message) => {
    dispatch({ type: OPEN_SNACKBAR, payload: message });
  };

  useEffect(() => {
    if (!isSnackBarOpen) return;

    const intervalId = setInterval(() => {
      dispatch({ type: CLOSE_SNACKBAR });
    }, 3000);

    return () => clearInterval(intervalId);
  }, [isSnackBarOpen]);

  return { isSnackBarOpen, openSnackBar, SnackBar };
};

export default useSnackBar;
