import styled from '@emotion/styled';
import COLOR from '../../constants/color';
import { SerializedStyles } from '@emotion/react';

interface ContainerProps {
  size: string;
  css?: SerializedStyles;
}

const CARD_SIZE = {
  EXTRA_SMALL: 'EXTRA_SMALL',
  SMALL: 'SMALL',
  LARGE: 'LARGE',
};

const sizeStyle = {
  EXTRA_SMALL: {
    padding: '3.3rem 3.3rem 2rem',
    minHeight: '10rem',
  },
  SMALL: {
    padding: '3.3rem 3.3rem 2rem',
    minHeight: '20rem',
  },
  LARGE: {
    padding: '5rem 4rem',
    minHeight: '48rem',
  },
};

const Container = styled.section<ContainerProps>`
  padding: 2.5rem;
  background-color: ${COLOR.WHITE};
  border-radius: 8px;
  border: 1px solid ${COLOR.LIGHT_GRAY_50};
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

  ${({ size }) => sizeStyle[size] || sizeStyle.SMALL}
  ${({ css }) => css}
`;

const Title = styled.div`
  font-size: 2rem;
  font-weight: 500;
  margin-bottom: 1rem;
`;

export { CARD_SIZE, Container, Title };
