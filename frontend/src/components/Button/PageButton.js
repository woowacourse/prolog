import React from 'react';
import Button from './Button';
import { css } from '@emotion/react';

const selectedStyle = css`
  background-color: #153147;
  color: white;
`;

const PageButton = ({ children, ...props }) => {
  return (
    <Button {...props} css={props.selected && selectedStyle}>
      {children}
    </Button>
  );
};

export default PageButton;
