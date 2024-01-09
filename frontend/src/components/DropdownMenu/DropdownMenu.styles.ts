import { InterpolationWithTheme } from '@emotion/core';
import { Theme } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div<{ css?: InterpolationWithTheme<Theme> }>`
  height: fit-content;
  max-height: 32rem;
  white-space: nowrap;
  overflow-y: auto;
  background-color: ${COLOR.WHITE};
  border-radius: 1.2rem;
  box-shadow: 0px 0px 6px ${COLOR.BLACK_OPACITY_300};
  margin-top: 1rem;
  padding: 1rem 1.2rem;
  position: absolute;
  z-index: 100;

  && {
    ${({ css }) => css}
  }

  & li {
    height: 4rem;
    display: flex;
    align-items: center;
    padding: 0 0.8rem;
    width: 100%;

    & > * {
      width: 100%;
      font-size: 1.4rem;
      font-weight: 500;
      color: ${COLOR.DARK_GRAY_900};
      transition: font-size 0.1s ease;
      text-align: left;
    }
  }

  & li:not(:last-child) {
    border-bottom: 0.7px solid ${COLOR.LIGHT_GRAY_700};
  }
`;

export { Container };
