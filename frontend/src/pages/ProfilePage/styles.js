import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  display: flex;
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
    max-height: 37rem;
  }
  & > *:nth-of-type(2) {
    grid-column-start: 2;
    grid-column-end: 3;
    min-height: 28rem;
  }
`;

const TagTitle = styled.div`
  font-size: 2rem;
  font-weight: 500;
  margin-bottom: 1rem;
`;

const TagContainer = styled.div`
  overflow-y: auto;
  height: 31rem;
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
  word-break: break-all;
`;

const Mission = styled.div`
  font-size: 1.2rem;
  color: ${COLOR.RED_400};
`;

const Title = styled.h3`
  margin-bottom: 0.6rem;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
`;

const Tags = styled.div`
  margin-top: auto;
  line-height: 1.5;
  flex: 1;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
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
  Content,
  Overview,
  TagTitle,
  CardStyles,
  PostItem,
  Mission,
  Tags,
  NoPost,
  Title,
  Description,
  PostBottomContainer,
  TagContainer,
};
