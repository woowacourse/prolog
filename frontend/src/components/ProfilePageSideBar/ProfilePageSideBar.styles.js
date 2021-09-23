import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 20rem;
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  background-color: ${COLOR.WHITE};
  border-radius: 1.6rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  padding-bottom: 1.8rem;
  height: fit-content;
`;

const Image = styled.img`
  width: 20rem;
  height: 20rem;
  border-top-left-radius: 1.6rem;
  border-top-right-radius: 1.6rem;
`;

const Nickname = styled.div`
  display: flex;
  align-items: center;
  font-size: 1.8rem;
  border-top: none;
  padding-left: 1.2rem;
`;

const Role = styled.div`
  margin-top: 1rem;
  padding-left: 1.2rem;
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
`;

const MenuList = styled.ul`
  margin-top: 2.4rem;
  display: flex;
  flex-direction: column;
  background-color: ${COLOR.WHITE};
  border-radius: 1.6rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  padding: 1.6rem 0;
`;

const MenuItem = styled.li`
  height: 4rem;
  display: flex;
  align-items: center;
  position: relative;

  ${({ isSelectedMenu }) =>
    isSelectedMenu &&
    css`
      font-weight: 700;
    `}

  & > button {
    ${({ isSelectedMenu }) =>
      isSelectedMenu &&
      css`
        color: ${COLOR.DARK_GRAY_900};
        font-weight: 500;
      `}
  }
  &:before {
    ${({ isSelectedMenu }) =>
      isSelectedMenu &&
      css`
        position: absolute;
        content: '';
        background-color: ${COLOR.LIGHT_GRAY_900};
        height: inherit;
        width: 0.5rem;
      `}
  }
`;

const MenuButton = styled.button`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  font-size: 1.6rem;
  color: ${COLOR.LIGHT_GRAY_900};
  font-weight: 300;
  padding: 2rem;
`;

const NicknameInput = styled.input`
  margin: 0.5rem 1.2rem;
  margin-right: 0;
  padding: 0.2rem 0.5rem;
  font-size: 1.6rem;
  outline: none;
  border-radius: 0.5rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_900};
  width: 12rem;
`;

const NicknameWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  word-break: break-all;
  align-items: center;
`;

const EditButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.WHITE};
  margin: 0 0.5rem;
  width: 5rem;
  height: 3rem;
  flex-shrink: 0;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

export {
  Profile,
  Image,
  Nickname,
  Role,
  MenuList,
  MenuItem,
  MenuButton,
  Container,
  NicknameInput,
  NicknameWrapper,
  EditButtonStyle,
};
