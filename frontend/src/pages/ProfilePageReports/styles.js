import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { NavLink } from 'react-router-dom';
import COLOR from '../../constants/color';

const Container = styled.section`
  width: 100%;

  ${({ reportsLength }) =>
    !reportsLength &&
    css`
      height: 70vh;

      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      p {
        margin: 0;
        font-size: 2rem;
        line-height: 1.5;
      }

      /* a {
        width: fit-content;
        padding: 1rem 3rem;
        margin: 1rem;
        border-radius: 1rem;
        color: ${COLOR.WHITE};
        background-color: ${COLOR.DARK_BLUE_800};

        :hover {
          background-color: ${COLOR.DARK_BLUE_900};
        }
      } */
    `}
`;

const AddNewReport = styled(NavLink)`
  width: fit-content;
  padding: 1rem 3rem;
  margin: 1rem;
  border-radius: 1rem;
  color: ${COLOR.WHITE};
  background-color: ${COLOR.DARK_BLUE_800};

  :hover {
    background-color: ${COLOR.DARK_BLUE_900};
  }
`;

export { Container, AddNewReport };
