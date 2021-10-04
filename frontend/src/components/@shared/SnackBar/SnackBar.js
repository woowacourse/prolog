import PropTypes from 'prop-types';
import { Container } from './SnackBar.styles';

const SnackBar = ({ children }) => <Container>{children}</Container>;

SnackBar.propTypes = {
  children: PropTypes.string.isRequired,
};

export default SnackBar;
