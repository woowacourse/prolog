import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

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
  background-color: ${COLOR.LIGHT_BLUE_400};

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

const Logo = styled.div`
  height: 3rem;
  position: relative;

  img {
    height: 100%;
    ${({ onClick }) => onClick && 'cursor: pointer;'}
  }

  span {
    font-size: 1.25rem;
    font-weight: 500;
    position: absolute;
    line-height: 1.5;
    padding: 0.1rem 0.5rem;
    top: -10%;
    left: 105%;
    color: white;
    background-color: ${COLOR.DARK_GRAY_900};
    border-radius: 2rem;
  }
`;

const Menu = styled.div`
  display: flex;

  & > *:not(:first-child) {
    margin-left: 1.6rem;
  }
`;

const DropdownStyle = css`
  top: 70px;
  right: 0px;
  transform: translateX(-24%);

  width: 16rem;

  & * {
    text-align: center;
  }
`;

const whiteBackgroundStyle = css`
  background-color: ${COLOR.WHITE};
`;

const pencilButtonStyle = css`
  width: 4.8rem;
  background-color: ${COLOR.DARK_BLUE_800};
`;

const profileButtonStyle = css`
  width: 4.8rem;
`;

export {
  Container,
  Wrapper,
  Logo,
  Menu,
  DropdownStyle,
  whiteBackgroundStyle,
  pencilButtonStyle,
  profileButtonStyle,
};
