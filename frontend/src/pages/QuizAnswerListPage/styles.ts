import styled from '@emotion/styled';
import MEDIA_QUERY from '../../constants/mediaQuery';

const HeaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1.5rem;

  ${MEDIA_QUERY.xs} {
    margin-bottom: 0.8rem;
  }
`;

const PostListContainer = styled.div`
  display: grid;
  grid-row-gap: 2rem;
  word-break: break-all;
`;

export { HeaderContainer, PostListContainer };
