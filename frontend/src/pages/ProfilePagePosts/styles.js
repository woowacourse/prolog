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

const CalendarWrapper = styled.div`
  margin-bottom: 2.4rem;
  background-color: ${COLOR.WHITE};
  border: 1px solid #e6e6e6;
  border-radius: 1.6rem;
  padding: 2.4rem;
`;

const PostItem = styled.div`
  width: 100%;
  padding: 2.4rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  /* border-top: 1px solid #e6e6e6; */
  display: gird;
  grid-template-columns: 8fr 2fr;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_50};
  }
`;

const PostList = styled.div`
  width: 100%;
  background-color: ${COLOR.WHITE};
  border: 1px solid #e6e6e6;
  border-radius: 1.6rem;
  padding: 2.4rem 0;

  & > *:not(:last-child) {
    border-bottom: 1px solid #e6e6e6;
  }
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
  margin: 1.4rem 0;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 80%;
`;

const Mission = styled.div`
  font-size: 1.6rem;
  color: ${COLOR.DARK_GRAY_900};
`;

const Title = styled.h3`
  font-size: 2.8rem;
  /* color: ${({ isHovered }) => (isHovered ? '#709EC0' : '#383838')}; */
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
  /* color: #709ec0;
  width: fit-content;
  height: fit-content; */
  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.RED_300};
  /* width: fit-content;
  color: #709ec0;
  height: fit-content; */

  &:hover {
    background-color: ${COLOR.RED_400};
  }
`;

export {
  Container,
  CalendarWrapper,
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
  PostList,
};
