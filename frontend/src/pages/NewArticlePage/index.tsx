import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import Button from '../../components/Button/Button';
import {
  ArticlePageContainer,
  Title,
  InputContainer,
  Input,
  Label,
  SubmitButtonStyle,
} from './styles';

import { usePostArticles } from '../../hooks/Articles/useArticles';
import { ArticleRequest } from '../../models/Article';
import { PATH } from '../../constants';

const NewArticlePage = () => {
  const [articleContent, setArticleContent] = useState<ArticleRequest>({
    title: '',
    link: '',
  });

  const history = useHistory();

  const { postArticle } = usePostArticles();

  const onArticleTitleChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, title: e.target.value });
  };

  const onArticleLinkChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, link: e.target.value });
  };

  const createArticle = () => {
    postArticle(articleContent);
    history.push(PATH.ARTICLE);
  };

  return (
    <ArticlePageContainer>
      <Title>📑 아티클 작성</Title>
      <InputContainer>
        <Label>제목</Label>
        <Input
          value={articleContent.title}
          placeholder="제목을 입력해주세요."
          onChange={onArticleTitleChanged}
        />
      </InputContainer>
      <InputContainer>
        <Label>링크</Label>
        <Input
          value={articleContent.link}
          placeholder="링크를 입력해주세요."
          onChange={onArticleLinkChanged}
        />
      </InputContainer>
      <Button type="button" size="X_SMALL" css={[SubmitButtonStyle]} onClick={createArticle}>
        작성 완료
      </Button>
    </ArticlePageContainer>
  );
};

export default NewArticlePage;
