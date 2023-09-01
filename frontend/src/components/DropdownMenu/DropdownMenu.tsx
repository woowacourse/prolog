import PropTypes from 'prop-types';
import { Container } from './DropdownMenu.styles';

const DropdownMenu = ({ children, css }) => {
  return <Container css={css}>{children}</Container>;
};

DropdownMenu.propTypes = {
  children: PropTypes.node,
};

export default DropdownMenu;
