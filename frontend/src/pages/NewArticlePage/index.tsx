import { useState } from 'react';
import Button from '../../components/Button/Button';
import {
  ArticlePageContainer,
  Title,
  InputContainer,
  Input,
  Label,
  SubmitButtonStyle,
} from './styles';

export interface ArticleForm {
  title: string;
  link: string;
}

const NewArticlePage = () => {
  const [articleContent, setArticleContent] = useState<ArticleForm>({
    title: '',
    link: '',
  });

  const onArticleTitleChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, title: e.target.value });
    console.log(e.target.value);
  };

  const onArticleLinkChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, link: e.target.value });
    console.log(e.target.value);
  };

  const onSubmit = () => {
    // post 요청
  };

  return (
    <ArticlePageContainer onSubmit={onSubmit}>
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
      <Button size="X_SMALL" type="submit" css={[SubmitButtonStyle]}>
        작성 완료
      </Button>
    </ArticlePageContainer>
  );
};

export default NewArticlePage;