import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  display: flex;
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

const Content = styled.div`
  width: 100%;
  height: fit-content;
  margin-left: 2.4rem;
  display: flex;
  justify-content: center;
`;

const Overview = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.6rem;

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

const TagTitle = styled.div`
  font-size: 2rem;
  font-weight: 500;
  margin-bottom: 1rem;
`;

const CardStyles = css`
  padding: 2rem;
  border-color: ${COLOR.LIGHT_GRAY_200};
`;

const PostItem = styled.div`
  width: 100%;
  padding: 1.6rem;
  cursor: pointer;
  display: flex;
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};
`;

const Mission = styled.div`
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.2rem;
  color: ${COLOR.RED_400};
`;

const Title = styled.h3`
  margin-bottom: 0.6rem;
`;

const Tags = styled.div`
  margin-top: auto;
  line-height: 1.5;
`;

const NoPost = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 80%;
`;

const PostBottomContainer = styled.div`
  display: flex;
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};

  & > *:not(:first-child) {
    margin-left: 1rem;
  }
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
  Overview,
  SideBar,
  TagTitle,
  CardStyles,
  PostItem,
  Mission,
  Tags,
  NoPost,
  Description,
  PostBottomContainer,
};
