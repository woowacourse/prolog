import { css } from '@emotion/react';
import styled from '@emotion/styled';

import COLOR from '../../constants/color';

const buttonStyle = css`
  width: fit-content;
  height: 3rem;
  margin: 0 0.5rem;
  padding: 0.8rem 2rem;

  display: flex;
  align-items: center;

  border-radius: 1rem;

  font-size: 1.3rem;
`;

const Container = styled.section`
  width: 100%;
  height: fit-content;
  margin-bottom: 2rem;

  a {
    ${buttonStyle};
  }
`;

const ReportHeader = styled.div`
  display: flex;
  align-items: center;
`;

const ReportBody = styled.div`
  width: 100%;
  margin: 1rem auto;
  padding: 2rem 1rem 5rem;

  position: relative;

  border-top: 0.1rem solid ${COLOR.LIGHT_GRAY_400};

  :hover {
    > div {
      visibility: visible;
    }
  }
`;

const ButtonWrapper = styled.div`
  position: absolute;
  right: 1rem;
  top: 1.5rem;

  display: flex;
  justify-content: space-between;
  align-items: center;

  visibility: hidden;

  a {
    background-color: transparent;
    border: 1px solid ${COLOR.LIGHT_GRAY_400};

    :hover {
      background-color: ${COLOR.LIGHT_GRAY_400};
    }
  }

  button {
    ${buttonStyle};

    background-color: ${COLOR.RED_300};
    border: 1px solid ${COLOR.RED_300};

    :hover {
      border: 1px solid ${COLOR.RED_400};
      background-color: ${COLOR.RED_400};
    }
  }
`;

export { Container, ReportHeader, ReportBody, ButtonWrapper };
