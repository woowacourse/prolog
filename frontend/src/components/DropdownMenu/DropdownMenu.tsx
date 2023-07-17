import PropTypes from 'prop-types';
import { Container } from './DropdownMenu.styles';
import { SerializedStyles } from '@emotion/react';

interface DropdownMenuProps {
  children?: React.ReactNode;
  css?: SerializedStyles;
  cssProps?: SerializedStyles;
}

const DropdownMenu = ({ children, cssProps, css }: DropdownMenuProps) => {
  return css && <Container css={cssProps || css}>{children}</Container>;
};

DropdownMenu.propTypes = {
  children: PropTypes.node,
};

export default DropdownMenu;
