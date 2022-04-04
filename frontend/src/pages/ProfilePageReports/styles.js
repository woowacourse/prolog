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

  :hover {
    color: ${COLOR.WHITE};
  }
`;

const Container = styled.section`
  width: 100%;
  height: fit-content;
  padding: 0 2rem;
  margin-bottom: 2rem;

  border: 2px solid ${COLOR.LIGHT_BLUE_200};
  border-radius: 1rem;

  a {
    ${buttonStyle};
  }
`;

const ReportBody = styled.div`
  width: 100%;
  margin: 1rem auto;
  padding: 2rem 1rem 5rem;

  position: relative;

  :hover {
    > div {
      visibility: visible;
    }
  }
`;

const ButtonWrapper = styled.div`
  position: absolute;
  right: 1rem;
  top: 6.5rem;

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

/** ReportInfo */
export const Section = styled.div`
  > span {
    font-size: 1.5rem;
    font-weight: 500;
    color: ${COLOR.BLACK_700};
  }

  > p {
    padding: 0 1rem;
    font-size: 1.4rem;
  }

  #report-title {
    font-size: 2rem;
    color: ${COLOR.BLACK_900};
    text-align: center;
  }
`;

export { Container, ReportBody, ButtonWrapper };
