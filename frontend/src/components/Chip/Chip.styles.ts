import { css } from '@emotion/react';
import styled from '@emotion/styled';

import COLOR from '../../constants/color';

import { getSize } from '../../utils/styles';
import { getTextColor } from '../../utils/textColorPicker';

export type ContainerProps = {
  /**
   * @default fit-content
   */
  width?: string | number;
  /**
   * @default none
   */
  maxWidth?: string | number;
  /**
   * @default COLOR.LIGHT_GRAY_200
   */
  backgroundColor?: string;
  /**
   * @default none
   */
  border?: string;
  /**
   * @default none
   */
  lineHeight?: string;
  /**
   * @default 1.4rem
   */
  marginRight?: string;
  /**
   * @default none
   */
  onClick?: () => void;
};

const Container = styled.div<ContainerProps>`
  width: ${({ width }) => (width ? getSize(width) : 'fit-content')};
  ${({ maxWidth }) => maxWidth && `max-width: ${getSize(maxWidth)}`};
  margin-right: ${({ marginRight }) => marginRight ?? '1.4rem'};
  padding: 0.2rem 0.8rem;

  display: flex;
  justify-content: center;
  align-items: center;

  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.LIGHT_GRAY_200};
  color: ${({ backgroundColor }) =>
    backgroundColor ? getTextColor(backgroundColor) : COLOR.BLACK_900};
  border-radius: 5rem;
  ${({ border }) => border && `border: ${border}`};

  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  ${({ lineHeight }) => lineHeight && `line-height: ${lineHeight}`};

  cursor: default;

  button {
    width: 1.6rem;
    height: 1.6rem;
    border-radius: 50%;
    background-color: inherit;
    margin-left: 0.3rem;

    display: inline-flex;
    justify-content: center;
    align-items: center;

    &:hover {
      background-color: ${COLOR.WHITE};

      svg {
        stroke: ${COLOR.BLACK_900};
      }
    }
  }

  ${({ onClick }) =>
    onClick &&
    css`
      cursor: pointer;

      :hover {
        filter: brightness(0.9);
      }
    `}
`;

export type ChipTextProps = {
  /**
   * @default center
   */
  textAlign?: string;
  /**
   * @default 1.4rem
   */
  fontSize?: string | number;
};

const ChipText = styled.span<ChipTextProps>`
  text-align: ${({ textAlign }) => (textAlign ? textAlign : 'center')};
  font-size: ${({ fontSize }) => (fontSize ? getSize(fontSize) : '1.4rem')};

  color: inherit;

  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
`;

export { Container, ChipText };
