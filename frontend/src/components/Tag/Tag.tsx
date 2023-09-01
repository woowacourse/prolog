import { Container, PostCount } from './Tag.styles';

interface Props {
  id: number;
  name: string;
  postCount: number;
  selectedTagId: number;
  onClick: () => void;
}

const Tag = ({ id, name, postCount, selectedTagId, onClick }: Props) => (
  <Container isSelected={id === selectedTagId} onClick={onClick}>
    #{name} <PostCount isSelected={id === selectedTagId}>{postCount}</PostCount>
  </Container>
);

export default Tag;
