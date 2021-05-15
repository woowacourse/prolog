import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useParams } from 'react-router';
import { Card, ProfileChip } from '../../components';
import db from '../../db.json';

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
  const { id } = useParams();
  const log = db.log.find((item) => item.logId === Number(id));
  const { logId, author, issuedDate, category, title, tags, content } = log;

  return (
    <Card key={logId} size="LARGE">
      <CardInner>
        <Header>
          <SubHeader>
            <Category>{category.categoryName}</Category>
            <IssuedDate>{Date(issuedDate)}</IssuedDate>
          </SubHeader>
          <Title>{title}</Title>
          <ProfileChip imageSrc={author.image} css={ProfileChipStyle}>
            {author.nickname}
          </ProfileChip>
        </Header>
        <Content>{content}</Content>
        <Tags>
          {tags.map((tag) => (
            <span key={tag}>{`#${tag} `}</span>
          ))}
        </Tags>
      </CardInner>
    </Card>
  );
};

export default PostPage;
