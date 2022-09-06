import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

export const NoDefaultHoverLink = styled(Link)`
  :hover {
    font-weight: unset;
  }
`;
