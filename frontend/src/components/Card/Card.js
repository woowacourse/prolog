import PropTypes from 'prop-types';
import { CARD_SIZE, Container } from './Card.styles';

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
