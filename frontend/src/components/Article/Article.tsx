import * as Styled from './Article.style';
import type { ArticleType } from '../../models/Article';
import Scrap from '../Reaction/Scrap';
import { useState } from 'react';

const Article = ({ title, userName, url, createdAt, imageUrl }: ArticleType) => {
  const [scrap, setScrap] = useState(false);

  const toggleScrap: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault();
    setScrap((prev) => !prev);

    // api
  };

  return (
    <Styled.Container>
      <Styled.Anchor href={url} target="_blank" rel="noopener noreferrer">
        <Styled.ThumbnailWrapper>
          <Styled.Thumbnail src={imageUrl} />
        </Styled.ThumbnailWrapper>
        <Styled.ArticleInfoContainer>
          <Styled.ArticleInfoWrapper>
            <Styled.UserName>{userName}</Styled.UserName>
            <Styled.CreatedAt>{createdAt.split(' ')[0]}</Styled.CreatedAt>
          </Styled.ArticleInfoWrapper>
          <Styled.Title>{title}</Styled.Title>
          <Styled.ScrapButtonWrapper>
            <Scrap scrap={scrap} onClick={toggleScrap} cssProps={Styled.ArticleScrapButtonStyle} />
          </Styled.ScrapButtonWrapper>
        </Styled.ArticleInfoContainer>
      </Styled.Anchor>
    </Styled.Container>
  );
};

export default Article;
