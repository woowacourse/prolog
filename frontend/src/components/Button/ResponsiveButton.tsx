import { css } from '@emotion/react';
import styled from '@emotion/styled';
import React, { ButtonHTMLAttributes } from 'react';
import { COLOR } from '../../enumerations/color';

interface ResponsiveButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text?: string;
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
  ...props
}: ResponsiveButtonProps) => {
  return (
    <StyledResponsiveButton
      fontSize={fontSize}
      color={color}
      backgroundColor={backgroundColor}
      height={height}
      {...props}
    >
      {text}
    </StyledResponsiveButton>
  );
};

export default ResponsiveButton;

export const StyledResponsiveButton = styled.button<
  Pick<ResponsiveButtonProps, 'fontSize' | 'color' | 'backgroundColor' | 'height'>
>`
  width: 100%;
  border-radius: 12px;
  text-align: center;
  padding: 0 10px;
  color: #fff;
  background-color: ${COLOR.LIGHT_BLUE_900};
  height: 50px;

  ${({ fontSize, color, backgroundColor, height }) => css`
    font-size: ${fontSize};
    color: ${color};
    background-color: ${backgroundColor};
    height: ${height};
  `};
`;
