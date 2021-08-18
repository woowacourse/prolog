import PropTypes from 'prop-types';
import { Container, PostCount } from './Tag.styles';

const Tag = ({ tag, postCount }) => {
  return (
    <Container>
      #{tag} <PostCount>{postCount}</PostCount>
    </Container>
  );
};

Tag.propTypes = {
  tag: PropTypes.string.isRequired,
};

export default Tag;
