import styled from '@emotion/styled';
import PropTypes from 'prop-types';

export const SIZE = {
  SMALL: 'SMALL',
  LARGE: 'LARGE',
};

const sizeStyle = {
  SMALL: {
    padding: '3.3rem 3.3rem 2rem',
    minHeight: '22.4rem',
  },
  LARGE: {
    padding: '5rem 4rem',
    minHeight: '64rem',
  },
};

const Container = styled.section`
  background-color: #ffffff;
  box-shadow: 0px 4px 4px #0000000d;
  border: 1px solid #c9c9c9;
  border-radius: 26px;
  box-sizing: border-box;

  ${({ size }) => sizeStyle[size] || sizeStyle.SMALL}
`;

const Card = ({ children, size }) => {
  return <Container size={size}>{children}</Container>;
};

Card.propTypes = {
  children: PropTypes.node.isRequired,
  size: PropTypes.string,
};

Card.defaultProps = {
  size: SIZE.SMALL,
};

export default Card;
