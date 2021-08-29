import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const Container = styled.div`
  border: 0.1rem solid ${COLOR.LIGHT_GRAY_200};
  width: fit-content;
  padding: 0.6rem 0.8rem;
  font-size: 1.4rem;
  border-radius: 0.6rem;
  display: inline-block;
  margin: 0 1.2rem 1.2rem 0;
  cursor: pointer;
  background-color: ${COLOR.WHITE};

  ${({ isSelected }) =>
    isSelected &&
    css`
      border: 0.1rem solid ${COLOR.LIGHT_BLUE_400};
      background-color: ${COLOR.LIGHT_BLUE_400};
    `}
`;

const PostCount = styled.span`
  color: ${COLOR.LIGHT_GRAY_500};
  margin-left: 0.4rem;

  ${({ isSelected }) =>
    isSelected &&
    css`
      color: ${COLOR.DARK_GRAY_900};
    `}
`;

export { Container, PostCount };
