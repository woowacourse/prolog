import * as Styled from './Article.style';
import type { ArticleType } from '../../models/Article';
import Scrap from '../Reaction/Scrap';
import { useRef, useState } from 'react';
import { usePutArticleBookmarkMutation } from '../../hooks/queries/article';
import debounce from '../../utils/debounce';

const Article = ({ id, title, userName, url, createdAt, imageUrl }: ArticleType) => {
  const bookmarkRef = useRef(false);
  const [bookmark, setBookmark] = useState(false);
  const { mutate: putBookmark } = usePutArticleBookmarkMutation();

  const toggleBookmark: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault();

    bookmarkRef.current = !bookmarkRef.current;
    setBookmark((prev) => !prev);

    debounce(() => {
      putBookmark({ articleId: id, bookmark: bookmarkRef.current });
    }, 300);
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
          <Styled.BookmarkWrapper>
            <Scrap
              scrap={bookmark}
              onClick={toggleBookmark}
              cssProps={Styled.ArticleBookmarkButtonStyle}
            />
          </Styled.BookmarkWrapper>
        </Styled.ArticleInfoContainer>
      </Styled.Anchor>
    </Styled.Container>
  );
};

export default Article;
