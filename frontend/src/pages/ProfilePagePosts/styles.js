import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: repeat(2, 1fr);

  & > * {
    grid-column-start: 1;
    grid-column-end: 3;
  }
  & > *:nth-of-type(2) {
    grid-column-start: 1;
    grid-column-end: 2;
    min-height: 28rem;
  }
  & > *:nth-of-type(3) {
    grid-column-start: 2;
    grid-column-end: 3;
    min-height: 28rem;
  }
`;

const PostItem = styled.div`
  width: 100%;
  padding: 2rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};
  display: gird;
  grid-template-columns: 8fr 2fr;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_50};
  }
`;

const TagTitle = styled.div`
  font-size: 2.4rem;
  font-weight: 500;
  margin-bottom: 1rem;
`;

const Content = styled.div`
  width: 100%;
  line-height: 1.5;
  white-space: normal;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  height: 4.8rem;
  word-break: break-all;
  margin: 1rem 0;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 80%;
`;

const Mission = styled.div`
  color: ${COLOR.LIGHT_GRAY_900};
  font-size: 1.2rem;
`;

const Title = styled.h3`
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
  line-height: 1.5;
`;

const ButtonList = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.6rem;

  visibility: ${({ isVisible }) => (isVisible ? 'visible' : 'hidden')};
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

const FilterListWrapper = styled.div`
  width: 100%;
  height: inherit;
  flex: 1;
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

const CardStyles = css`
  padding: 2rem;
  border-color: ${COLOR.LIGHT_GRAY_200};
`;

export {
  Container,
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
  TagTitle,
  FilterListWrapper,
  HeaderContainer,
  CardStyles,
};
