import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useParams } from 'react-router';
import { Card, ProfileChip } from '../../components';
import useGetFetch from '../../hooks/useGetFetch';

const CardInner = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;

  & > *:not(:last-child) {
    margin-bottom: 6rem;
  }
`;

const Header = styled.div``;

const SubHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Category = styled.div`
  font-size: 2rem;
  color: #383838;
  font-weight: lighter;
`;

const Title = styled.div`
  font-size: 3.6rem;
  color: #383838;
  font-weight: bold;
  margin-bottom: 2rem;
`;

const Content = styled.div`
  line-break: anywhere;
  white-space: break-spaces;
`;

const Tags = styled.div`
  font-size: 1.4rem;
  color: #848484;
  margin-top: auto;
`;

const IssuedDate = styled.div`
  color: #444444;
  font-size: 1.4rem;
`;

const ProfileChipStyle = css`
  border: none;
`;

const PostPage = () => {
  const { id: postId } = useParams();
  const { response: post, error } = useGetFetch({}, 'getData', postId);
  const { id, author, createdAt, category, title, tags, content } = post;

  if (error) {
    <>해당 글을 찾을 수 없습니다.</>;
  }

  return (
    <Card key={id} size="LARGE">
      <CardInner>
        <Header>
          <SubHeader>
            <Category>{category?.name}</Category>
            <IssuedDate>{Date(createdAt)}</IssuedDate>
          </SubHeader>
          <Title>{title}</Title>
          <ProfileChip imageSrc={author?.imageUrl} css={ProfileChipStyle}>
            {author?.nickName}
          </ProfileChip>
        </Header>
        <Content>{content}</Content>
        <Tags>
          {tags?.map((tag) => (
            <span key={tag}>{`#${tag} `}</span>
          ))}
        </Tags>
      </CardInner>
    </Card>
  );
};

export default PostPage;
