import styled from '@emotion/styled';

const HeaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1.5rem;

  @media screen and (max-width: 420px) {
    margin-bottom: 0.8rem;
  }
`;

const PostListContainer = styled.div`
  display: grid;
  grid-row-gap: 2rem;
  word-break: break-all;
`;

export { HeaderContainer, PostListContainer };
