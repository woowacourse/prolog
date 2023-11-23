/** @jsxImportSource @emotion/react */

// import PropTypes from 'prop-types';
import { Container } from './DropdownMenu.styles';
import { css } from '@emotion/react';

export interface DropdownMenuProps {
  css: ReturnType<typeof css>;
}

const DropdownMenu = ({ children, css }: React.PropsWithChildren<DropdownMenuProps>) => {
  return <Container css={css}>{children}</Container>;
};

export default DropdownMenu;
