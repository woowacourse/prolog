import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div`
  border: 0.1rem solid #e6e6e6;
  width: fit-content;
  padding: 0.4rem 0.6rem;
  font-size: 1.4rem;
  border-radius: 0.6rem;
  display: inline-block;
  margin: 0 1.2rem 1.2rem 0;
  cursor: pointer;

  ${({ isSelected }) =>
    isSelected &&
    css`
      border: 0.1rem solid #a9cbe5;
      background-color: #a9cbe5;
    `}
`;

const PostCount = styled.span`
  color: #aaaaaa;

  ${({ isSelected }) =>
    isSelected &&
    css`
      color: #333333;
    `}
`;

export { Container, PostCount };
