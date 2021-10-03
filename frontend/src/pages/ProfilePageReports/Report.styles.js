import styled from '@emotion/styled';

import { COLOR } from '../../constants';

const Wrapper = styled.article`
  width: 100%;
  margin: 1rem auto;
  padding: 2rem 1rem 5rem;
  color: ${COLOR.BLACK_800};

  border-top: 0.1rem solid ${COLOR.LIGHT_GRAY_400};

  > h2 {
    padding: 0 0.2rem;

    font-size: 2rem;
    font-weight: 500;
  }

  > p {
    margin: 1rem 0 3rem;
    padding: 0 0.2rem;

    font-size: 1.5rem;
    font-weight: 400;
  }
`;

export { Wrapper };
