import styled from '@emotion/styled';
import { NavLink } from 'react-router-dom';
import { COLOR } from '../../constants';

export const AddNewReportLink = styled(NavLink)`
  background-color: ${COLOR.DARK_BLUE_800};
  color: ${COLOR.WHITE};

  :hover {
    background-color: ${COLOR.DARK_BLUE_900};
  }
`;
