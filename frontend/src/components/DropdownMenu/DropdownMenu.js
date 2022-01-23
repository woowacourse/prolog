import PropTypes from 'prop-types';
import { Container } from './DropdownMenu.styles';

const DropdownMenu = ({ children, cssProps, css }) => {
  return <Container css={cssProps || css}>{children}</Container>;
};

DropdownMenu.propTypes = {
  children: PropTypes.node,
};

export default DropdownMenu;
