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
  height: 4.8rem;
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

  @media screen and (max-width: 420px) {
    padding: 0 1rem;
  }
`;

const Logo = styled.div`
  height: 2.4rem;
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

  & > * {
    margin-left: 1rem;

    @media screen and (max-width: 420px) {
      margin-left: 0.5rem;
    }
  }

  & > form {
    margin-left: 0;
  }
`;

const DropdownStyle = css`
  top: 4.6rem;
  right: 0px;
  transform: translateX(-24%);

  width: 16rem;

  & * {
    text-align: center;
  }

  @media screen and (max-width: 420px) {
    transform: translateX(-10%);
  }
`;

const Navigation = styled.nav`
  margin-right: 1rem;

  display: flex;
  align-items: center;

  > a {
    padding-left: 0.5rem;
    padding-right: 0.5rem;
  }

  > *:not(:last-child) {
    margin-right: 2rem;
  }
`;

const whiteBackgroundStyle = css`
  background-color: ${COLOR.WHITE};
  color: ${COLOR.BLACK_800};
`;

export const loginButtonStyle = css`
  width: 9rem;
  height: 3.6rem;
  padding-right: 0.3rem;

  display: flex;
  justify-content: center;
  align-items: center;

  background-color: ${COLOR.WHITE};
  border-radius: 1rem;

  font-size: 1.8rem;
  color: ${COLOR.BLACK_800};

  img {
    width: 2.4rem;
    height: 2.4rem;
    margin: 0;
  }
`;

const pencilButtonStyle = css`
  width: 3.6rem;
  height: 3.6rem;

  background-color: ${COLOR.DARK_BLUE_800};
  border-radius: 1.2rem;

  img {
    width: 2.4rem;
    height: 2.4rem;
  }
`;

const profileButtonStyle = css`
  width: 3.6rem;
  height: 3.6rem;

  border-radius: 1.2rem;
`;

export {
  Container,
  Wrapper,
  Logo,
  Menu,
  Navigation,
  DropdownStyle,
  whiteBackgroundStyle,
  pencilButtonStyle,
  profileButtonStyle,
};
