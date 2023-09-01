import * as Styled from './Article.style';
import type { ArticleType } from '../../models/Article';

const Article = ({ id, title, userName, url, createdAt, imageUrl }: ArticleType) => {
  console.log(imageUrl);
  return (
    <Styled.Container>
      <Styled.Anchor href={url} target="_blank" rel="noopener noreferrer">
        <Styled.ThumbnailWrapper>
          <Styled.Thumbnail src={imageUrl} />
        </Styled.ThumbnailWrapper>
        <Styled.ArticleInfoContainer>
          <Styled.UserName>{userName}</Styled.UserName>
          <Styled.Title>{title}</Styled.Title>
          <Styled.CreatedAt>{createdAt}</Styled.CreatedAt>
        </Styled.ArticleInfoContainer>
      </Styled.Anchor>
    </Styled.Container>
  );
};

export default Article;
