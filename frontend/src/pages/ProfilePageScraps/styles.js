import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 814px;
`;

const PostItem = styled.div`
  width: 100%;
  padding: 2rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};
  word-break: break-all;

  &:hover {
    background-color: ${COLOR.LIGHT_GRAY_50};

    /* ButtonList에 대한 조건, 추후 @emotion/babel-plugin을 적용하여, components as selectors로 변경예정 */
    > div:last-child {
      visibility: visible;
    }
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
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_900};
  margin-top: auto;
  line-height: 1.5;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
`;

const ButtonList = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.6rem;

  visibility: hidden;
`;

const NoPost = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const DeleteButtonStyle = css`
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.RED_300};

  &:hover {
    background-color: ${COLOR.RED_400};
  }
`;

const CardStyles = css`
  padding: 2rem;
  border-color: ${COLOR.LIGHT_GRAY_200};
`;

const Heading = styled.h1`
  font-size: 2.4rem;
  margin-bottom: 2rem;
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
  DeleteButtonStyle,
  Heading,
  CardStyles,
};
