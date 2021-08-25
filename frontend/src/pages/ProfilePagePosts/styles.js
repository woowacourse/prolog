import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

const HeaderContainer = styled.div`
  height: 4.8rem;
  display: flex;
  margin-bottom: 3.7rem;
  justify-content: space-between;

  & > *:not(:first-child) {
    margin-left: 2rem;
  }
`;

const PostListContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: ${COLOR.WHITE};
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  border-radius: 1.6rem;
  padding: 2.4rem;
`;

const PostItem = styled.div`
  height: 18rem;
  padding: 2.4rem 1.6rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;

  &:not(:last-child) {
    border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_50};
  }
`;

const Content = styled.div`
  display: flex;
  height: 100%;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const Mission = styled.div`
  font-size: 1.6rem;
  color: ${COLOR.DARK_GRAY_900};
`;

const Title = styled.h3`
  font-size: 2.8rem;

  color: ${COLOR.DARK_GRAY_900};
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
`;

const ButtonList = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.6rem;
`;

const NoPost = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const EditButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.WHITE};

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.RED_300};

  &:hover {
    background-color: ${COLOR.RED_400};
  }
`;

export {
  Container,
  HeaderContainer,
  PostListContainer,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  PostItem,
  ButtonList,
  NoPost,
  EditButtonStyle,
  DeleteButtonStyle,
};
