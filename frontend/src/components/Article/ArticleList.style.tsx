import styled from '@emotion/styled';
import MEDIA_QUERY from '../../constants/mediaQuery';

export const Container = styled.ul`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-auto-rows: 1fr;
  gap: 30px 64px;

  ${MEDIA_QUERY.xl} {
    gap: 30px 40px;
  }

  ${MEDIA_QUERY.lg} {
    grid-template-columns: repeat(2, 1fr);
  }

  ${MEDIA_QUERY.md} {
    grid-template-columns: repeat(1, 1fr);
  }
`;
