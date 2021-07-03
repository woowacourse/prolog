import styled from '@emotion/styled';
import { css } from '@emotion/react';

const HeaderContainer = styled.div`
  height: 4.8rem;
  display: flex;
  margin-bottom: 3.7rem;
  justify-content: space-between;

  & > *:not(:first-child) {
    margin-left: 2rem;
  }
`;

const FilterListWrapper = styled.div`
  width: 100%;
  height: inherit;
  flex: 1;
`;

const PostListContainer = styled.div`
  display: grid;
  grid-row-gap: 2rem;
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
  color: #383838;
`;

const Title = styled.h3`
  font-size: 2.8rem;
  color: #383838;
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: #848484;
  margin-top: auto;
`;

const ProfileChipLocationStyle = css`
  margin-left: auto;
`;

const CardHoverStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;

  &:hover {
    transform: scale(1.005);
  }
`;

const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 3rem;

  *:not(:first-child) {
    margin-left: 1rem;
  }
`;

const PageButtonStyle = css`
  background-color: transparent;
  transition: transform 0.1s ease;

  :hover {
    transform: translateY(-20%);
  }
`;

const PageSkipButtonStyle = css`
  background-color: #e5e5e5;

  :hover {
    filter: brightness(0.9);
  }
`;

export {
  HeaderContainer,
  FilterListWrapper,
  PostListContainer,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  ProfileChipLocationStyle,
  CardHoverStyle,
  PaginationContainer,
  PageButtonStyle,
  PageSkipButtonStyle,
};
