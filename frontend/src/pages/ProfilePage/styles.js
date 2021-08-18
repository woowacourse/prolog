import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 1.6rem;
  border: 1px solid #e6e6e6;
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
  color: #888888;
`;

const MenuList = styled.ul`
  margin-top: 2.4rem;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 1.6rem;
  border: 1px solid #e6e6e6;
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
        color: #333;
        font-weight: 500;
      `}
  }
  &:before {
    ${({ isSelectedMenu }) =>
      isSelectedMenu &&
      css`
        position: absolute;
        content: '';
        background-color: #888888;
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
  color: #888888;
  font-weight: 300;
  padding: 2rem;
`;

const Content = styled.div`
  width: 100%;
  height: fit-content;
  margin-left: 2.4rem;
  display: flex;
  justify-content: center;
`;

const Title = styled.div`
  font-size: 2.8rem;
  font-weight: 500;
`;

const Preparing = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);

  & > * {
    grid-column-start: 1;
    grid-column-end: 3;
  }
  & > *:nth-of-type(1) {
    grid-column-start: 1;
    grid-column-end: 2;
    min-height: 28rem;
  }
  & > *:nth-of-type(2) {
    grid-column-start: 2;
    grid-column-end: 3;
    min-height: 28rem;
  }
`;

const SideBar = styled.div`
  display: flex;
  flex-direction: column;
`;

export {
  Container,
  Profile,
  Image,
  Nickname,
  Role,
  MenuList,
  MenuItem,
  MenuButton,
  Content,
  Title,
  Preparing,
  SideBar,
};
