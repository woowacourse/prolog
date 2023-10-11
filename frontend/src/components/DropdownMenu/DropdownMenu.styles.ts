import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import { css } from '@emotion/react';
import MEDIA_QUERY from '../../constants/mediaQuery';

const Container = styled.div<{
  css: ReturnType<typeof css>;
}>`
  height: fit-content;
  max-height: 32rem;
  white-space: nowrap;
  overflow-y: auto;
  background-color: ${COLOR.WHITE};
  border-radius: 1.2rem;
  box-shadow: 0px 0px 6px ${COLOR.BLACK_OPACITY_300};
  padding: 1rem 1.2rem;
  position: absolute;
  z-index: 100;
  right: 30px;
  top: 50px;

  ${MEDIA_QUERY.xs} {
    right: 10px;
    top: 40px;
  }
  /* transform: translateY(30%); */

  && {
    ${(props) => props.css}
  }

  /* &:before {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: -1;
    content: ' ';
    background-color: rgba(0, 0, 0, 0.4);
  } */

  /* 삼각형 입니다 ^^
    &:before {
    content: '';
    position: absolute;
    border-style: solid;
    border-width: 0 14px 15px;
    border-color: #ffffff transparent;
    display: block;
    width: 0;
    z-index: 0;
    top: -14px;
    right: 16px;
  }

  &:after {
    content: '';
    position: absolute;
    border-style: solid;
    border-width: 0 14px 15px;
    border-color: #ffffff transparent;
    display: block;
    width: 0;
    z-index: 0;
    top: -14px;
    right: 16px;
  } */

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

  /* & li:hover {
    & > * {
      font-size: 2.2rem;
    }
  } */
`;

export { Container };
