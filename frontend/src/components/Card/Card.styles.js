import styled from '@emotion/styled';
import COLOR from '../../constants/color';

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

const Container = styled.section`
  background-color: ${COLOR.WHITE};
  border: 1px solid ${COLOR.LIGHT_GRAY_400};
  border-radius: 2rem;

  ${({ size }) => sizeStyle[size] || sizeStyle.SMALL}
  ${({ css }) => css}
`;

const Title = styled.div`
  font-size: 2rem;
  font-weight: 500;
  margin-bottom: 1rem;
`;

export { CARD_SIZE, Container, Title };
