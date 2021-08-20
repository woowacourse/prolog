import styled from '@emotion/styled';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

const HeaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1.5rem;
`;

const FilterListWrapper = styled.div`
  height: 4.8rem;
  flex-grow: 1;
  margin-right: 1rem;
`;

const SelectedFilterList = styled.div`
  width: 100%;
  height: 3rem;
  overflow: scroll;
  margin-top: 1rem;

  ul {
    width: max-content;
    display: flex;
  }
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

const ProfileChipLocationStyle = css`
  margin-left: auto;

  &:hover {
    background-color: ${COLOR.LIGHT_BLUE_100};
  }
`;

const CardHoverStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;

  &:hover {
    transform: scale(1.005);
  }
`;

export {
  HeaderContainer,
  FilterListWrapper,
  SelectedFilterList,
  PostListContainer,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  ProfileChipLocationStyle,
  CardHoverStyle,
};
