import * as Styled from './Article.style';
import type { ArticleType } from '../../models/Article';
import Scrap from '../Reaction/Scrap';
import { useContext, useRef, useState } from 'react';
import {
  usePostArticleViewsMutation,
  usePutArticleBookmarkMutation,
  usePutArticleLikeMutation,
} from '../../hooks/queries/article';
import debounce from '../../utils/debounce';
import Like from '../Reaction/Like';
import { UserContext } from '../../contexts/UserProvider';

const Article = ({
  id,
  title,
  userName,
  url,
  createdAt,
  publishedAt,
  imageUrl,
  isBookmarked,
  isLiked,
}: ArticleType) => {
  const bookmarkRef = useRef(isBookmarked);
  const likeRef = useRef(isLiked);
  const [bookmark, setBookmark] = useState(isBookmarked);
  const [like, setLike] = useState(isLiked);
  const { mutate: putBookmark } = usePutArticleBookmarkMutation();
  const { mutate: putLike } = usePutArticleLikeMutation();
  const { mutate: postViews } = usePostArticleViewsMutation();

  const { user } = useContext(UserContext);
  const { isLoggedIn } = user;

  const toggleBookmark: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault();
    e.stopPropagation();

    if (!isLoggedIn) {
      alert('로그인을 해주세요');
      return;
    }

    bookmarkRef.current = !bookmarkRef.current;
    setBookmark((prev) => !prev);

    debounce(() => {
      putBookmark({ articleId: id, bookmark: bookmarkRef.current });
    }, 300);
  };

  const toggleLike: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    e.preventDefault();
    e.stopPropagation();

    if (!isLoggedIn) {
      alert('로그인을 해주세요');
      return;
    }

    likeRef.current = !likeRef.current;
    setLike((prev) => !prev);

    debounce(() => {
      putLike({ articleId: id, like: likeRef.current });
    }, 300);
  };

  return (
    <Styled.Container onClick={() => postViews(id)}>
      <Styled.Anchor href={url} target="_blank" rel="noopener noreferrer">
        <Styled.ThumbnailWrapper>
          <Styled.Thumbnail src={imageUrl} />
        </Styled.ThumbnailWrapper>
        <Styled.ArticleInfoContainer>
          <Styled.ArticleInfoWrapper>
            <Styled.UserName>{userName}</Styled.UserName>
            <Styled.PublishedAt>{(publishedAt ? publishedAt : createdAt).split(' ')[0]}</Styled.PublishedAt>
          </Styled.ArticleInfoWrapper>
          <Styled.Title>{title}</Styled.Title>
          <Styled.ButtonContainer>
            <Like liked={like} onClick={toggleLike} />
            <Scrap
              scrap={bookmark}
              onClick={toggleBookmark}
              cssProps={Styled.ArticleBookmarkButtonStyle}
            />
          </Styled.ButtonContainer>
        </Styled.ArticleInfoContainer>
      </Styled.Anchor>
    </Styled.Container>
  );
};

export default Article;
