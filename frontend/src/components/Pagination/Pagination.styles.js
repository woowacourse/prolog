import styled from '@emotion/styled';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 3rem;

  *:not(:first-of-type) {
    margin-left: 1rem;
  }
`;

const PageButtonStyle = css`
  background-color: transparent;
  color: ${COLOR.BLACK_800};
  transition: transform 0.1s ease;

  :hover {
    transform: translateY(-20%);
  }
`;

const PageSkipButtonStyle = css`
  background-color: ${COLOR.LIGHT_GRAY_200};
  color: ${COLOR.BLACK_800};

  :disabled {
    visibility: hidden;
  }

  :hover {
    filter: brightness(0.9);
  }
`;

export { PaginationContainer, PageButtonStyle, PageSkipButtonStyle };
