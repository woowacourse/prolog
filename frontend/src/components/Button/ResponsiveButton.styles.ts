import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR } from '../../enumerations/color';
import { ResponsiveButtonProps } from './ResponsiveButton';

export const Root = styled.button<
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
