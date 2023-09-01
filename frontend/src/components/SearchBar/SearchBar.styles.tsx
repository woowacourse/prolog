import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import { css } from '@emotion/react';

const Container = styled.div<{
  css: ReturnType<typeof css>;
}>`
  background-color: ${COLOR.LIGHT_GRAY_100};
  height: 4.8rem;
  width: 36rem;

  padding: 0.5rem 1rem;
  border-radius: 1.6rem;

  display: flex;
  justify-content: center;
  align-items: center;

  input {
    height: 90%;
    flex-grow: 1;

    border: 0;
    outline: none;
    border-radius: 1rem;

    padding: 0 1rem;
    margin-right: 1rem;

    :focus {
      border: 2px solid ${COLOR.LIGHT_GRAY_400};
    }
  }

  button {
    width: 3.6rem;
    background-color: transparent;
  }

  ${({ css }) => css}
`;

export { Container };
