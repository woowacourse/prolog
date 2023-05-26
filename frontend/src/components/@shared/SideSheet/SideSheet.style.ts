import styled from '@emotion/styled';
import { css, keyframes } from '@emotion/react';
import { SideSheetProps } from './SideSheet';
import MOBILE_MAX_SIZE from '../../../constants/screenSize';

export type SideSheetContentProps = Pick<SideSheetProps, 'width'>;

const slide = keyframes`
  from{
    transform: translateX(100%);
  }
  to {
    transform: translateX(0);
  }
`;

export const SideSheetContent = styled.div<SideSheetContentProps>`
  ${({ width }) => css`
    position: fixed;
    top: 0;
    right: 0;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    width: ${width ?? '500px'};
    height: 100%;
    overflow-y: auto;
    padding: 34px 0;
    background-color: #fff;
    z-index: 101;
    animation: 0.25s ease-in forwards ${slide};
  `}
   @media (max-width: ${MOBILE_MAX_SIZE}) {
     top: initial;
     bottom: 0;
     height: 50%;
     width: 100%;
    }
`;

export const Backdrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 100;
`;
