import { Theme } from '@emotion/react';
import { InterpolationWithTheme } from '@emotion/core';
import { PropsWithChildren } from 'react';
import { Container } from './DropdownMenu.styles';

type DropdownMenuProps = PropsWithChildren<{
  css?: InterpolationWithTheme<Theme>;
}>

const DropdownMenu = ({ children, css }: DropdownMenuProps) => {
  return <Container css={css}>{children}</Container>;
};

export default DropdownMenu;
