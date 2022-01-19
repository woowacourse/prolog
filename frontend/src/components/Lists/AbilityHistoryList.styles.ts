import styled from '@emotion/styled';
import { COLOR } from '../../constants';

export const List = styled.ul`
  width: 100%;
  height: 15rem;
  overflow: auto;
`;

export const AbilityHistory = styled.li`
  width: 100%;

  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_300};

  a {
    width: 100%;
    padding: 1rem;
    display: block;

    color: ${COLOR.DARK_GRAY_700};
    font-size: 1.3rem;

    :hover {
      font-weight: 500;

      background-color: ${COLOR.LIGHT_GRAY_100};
    }
  }
`;
