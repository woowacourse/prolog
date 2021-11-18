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
  min-height: 3rem;
  overflow: auto;
  margin-top: 1rem;
  padding-bottom: 1rem;

  ul {
    width: max-content;
    display: flex;
  }
`;

const PostListContainer = styled.div`
  display: grid;
  grid-row-gap: 2rem;
  word-break: break-all;
`;

export { HeaderContainer, FilterListWrapper, SelectedFilterList, PostListContainer };
