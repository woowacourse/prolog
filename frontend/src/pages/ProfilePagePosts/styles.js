import { css } from '@emotion/react';
import styled from '@emotion/styled';

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

const PostItem = styled.div`
  width: 100%;
  padding: 2.4rem 1.6rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  border-top: 1px solid #e6e6e6;
  display: gird;
  grid-template-columns: 8fr 2fr;

  &:hover {
    background-color: #f9f9f9;
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
  font-size: 1.4rem;
  color: #383838;
`;

const Title = styled.h3`
  font-size: 2.6rem;
  /* color: ${({ isHovered }) => (isHovered ? '#709EC0' : '#383838')}; */
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: #777777;
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
  border: 1px solid #e6e6e6;
  background-color: #fff;
  /* color: #709ec0;
  width: fit-content;
  height: fit-content; */
  &:hover {
    background-color: #e8e8e8;
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid #e6e6e6;
  background-color: #f59898;
  /* width: fit-content;
  color: #709ec0;
  height: fit-content; */

  &:hover {
    background-color: #f08484;
  }
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
};
