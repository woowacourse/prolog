import PropTypes from 'prop-types';
import { Container, PostCount } from './Tag.styles';

const Tag = ({ id, name, postCount, selectedTagId, onClick }) => (
  <Container isSelected={id === selectedTagId} onClick={onClick}>
    #{name} <PostCount isSelected={id === selectedTagId}>{postCount}</PostCount>
  </Container>
);

Tag.propTypes = {
  id: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
  postCount: PropTypes.number.isRequired,
  selectedTagId: PropTypes.number.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default Tag;
