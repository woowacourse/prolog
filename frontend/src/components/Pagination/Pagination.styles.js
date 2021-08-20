import styled from '@emotion/styled';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 3rem;

  *:not(:first-child) {
    margin-left: 1rem;
  }
`;

const PageButtonStyle = css`
  background-color: transparent;
  transition: transform 0.1s ease;

  :hover {
    transform: translateY(-20%);
  }
`;

const PageSkipButtonStyle = css`
  background-color: ${COLOR.LIGHT_GRAY_200};

  :disabled {
    visibility: hidden;
  }

  :hover {
    filter: brightness(0.9);
  }
`;

export { PaginationContainer, PageButtonStyle, PageSkipButtonStyle };
