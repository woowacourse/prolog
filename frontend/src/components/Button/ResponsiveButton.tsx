import { css } from '@emotion/react';
import styled from '@emotion/styled';
import React, { ButtonHTMLAttributes } from 'react';

interface ResponsiveButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text: string;
  fontSize?: string;
  color?: string;
  backgroundColor?: string;
  height?: string;
}

const ResponsiveButton = ({
  text,
  fontSize,
  color,
  backgroundColor,
  height,
}: ResponsiveButtonProps) => {
  return (
    <StyledResponsiveButton color={color} backgroundColor={backgroundColor} height={height}>
      {text}
    </StyledResponsiveButton>
  );
};

export default ResponsiveButton;

const StyledResponsiveButton = styled.button<Omit<ResponsiveButtonProps, 'text'>>`
  width: 100%;
  border-radius: 12px;
  text-align: center;

  ${({ fontSize, color, backgroundColor, height }) => css`
    font-size: ${fontSize || '16px'};
    color: ${color};
    background-color: ${backgroundColor};
    height: ${height || '50px'};
  `};
`;
