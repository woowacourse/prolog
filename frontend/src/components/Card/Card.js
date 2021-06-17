import styled from '@emotion/styled';
import PropTypes from 'prop-types';

export const CARD_SIZE = {
  SMALL: 'SMALL',
  LARGE: 'LARGE',
};

const sizeStyle = {
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
  background-color: #ffffff;
  box-shadow: 0px 4px 4px #0000000d;
  border: 1px solid #c9c9c9;
  border-radius: 2rem;

  ${({ css }) => css}
  ${({ size }) => sizeStyle[size] || sizeStyle.SMALL}
`;

const Card = ({ children, size, css, onClick }) => {
  return (
    <Container size={size} css={css} onClick={onClick}>
      {children}
    </Container>
  );
};

Card.propTypes = {
  children: PropTypes.node.isRequired,
  size: PropTypes.string,
  css: PropTypes.object,
  onClick: PropTypes.func,
};

Card.defaultProps = {
  size: CARD_SIZE.SMALL,
};

export default Card;
