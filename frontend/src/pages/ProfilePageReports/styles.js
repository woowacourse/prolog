import { css } from '@emotion/react';
import styled from '@emotion/styled';
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

      button {
        width: fit-content;
        padding: 1rem 3rem;
        margin: 1rem;
        border-radius: 1rem;

        :hover {
          background-color: ${COLOR.DARK_BLUE_900};
        }
      }
    `}
`;

export { Container };
