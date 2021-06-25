import { css } from '@emotion/react';
import styled from '@emotion/styled';

const DropdownToggledStyle = css`
  &:before {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 80;
    display: block;
    cursor: default;
    content: ' ';
    background: transparent;
  }
`;

const Container = styled.div`
  width: 100%;
  height: 6.4rem;
  background-color: #a9cbe5;

  ${({ isDropdownToggled }) => isDropdownToggled && DropdownToggledStyle}
`;

const Wrapper = styled.div`
  position: relative;
  max-width: 112rem;
  padding: 0 4rem;
  height: 100%;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Logo = styled.img`
  height: 3rem;
  ${({ onClick }) => onClick && 'cursor: pointer;'}
`;

const Menu = styled.div`
  display: flex;

  & > *:not(:first-child) {
    margin-left: 1.6rem;
  }
`;

const DropdownLocationStyle = css`
  top: 70px;
  right: 0px;
`;

const whiteBackgroundStyle = css`
  background-color: #ffffff;
`;

export { Container, Wrapper, Logo, Menu, DropdownLocationStyle, whiteBackgroundStyle };
