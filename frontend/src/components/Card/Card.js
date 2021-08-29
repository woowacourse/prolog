import PropTypes from 'prop-types';
import { CARD_SIZE, Container, Title } from './Card.styles';

const Card = ({ title, children, size, css, onClick }) => {
  return (
    <div>
      {title && <Title>{title} </Title>}
      <Container size={size} css={css} onClick={onClick}>
        {children}
      </Container>
    </div>
  );
};

Card.propTypes = {
  children: PropTypes.node.isRequired,
  title: PropTypes.string,
  size: PropTypes.string,
  css: PropTypes.object,
  onClick: PropTypes.func,
};

Card.defaultProps = {
  title: '',
  size: CARD_SIZE.SMALL,
};

export default Card;
