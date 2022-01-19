import React from 'react';
import Button from './Button';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

const selectedStyle = css`
  background-color: ${COLOR.DARK_BLUE_800};
  color: ${COLOR.WHITE};
`;

const unselectedStyle = css`
  background-color: transparent;
  color: ${COLOR.BLACK_800};
`;

const PageButton = ({ children, ...props }) => {
  return (
    <Button {...props} css={props.selected ? selectedStyle : unselectedStyle}>
      {children}
    </Button>
  );
};

export default PageButton;
