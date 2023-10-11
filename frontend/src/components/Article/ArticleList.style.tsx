import styled from '@emotion/styled';
import MEDIA_QUERY from '../../constants/mediaQuery';

export const Container = styled.ul`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-auto-rows: 1fr;
  gap: 10px;

  ${MEDIA_QUERY.lg} {
    grid-template-columns: repeat(3, 1fr);
  }

  ${MEDIA_QUERY.md} {
    grid-template-columns: repeat(2, 1fr);
  }

  ${MEDIA_QUERY.sm} {
    grid-template-columns: repeat(1, 1fr);
  }
`;
