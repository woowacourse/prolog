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
  ThumbnailImage,
  ThumbnailContainer,
} from './styles';

import { usePostMetaOg, usePostArticles } from '../../hooks/Articles/useArticles';
import { ArticleRequest } from '../../models/Article';
import { PATH } from '../../constants';

const NewArticlePage = () => {
  const [isValidate, setIsValidate] = useState<boolean>(true);
  const [isButton, setisButton] = useState<boolean>(false);

  const [articleContent, setArticleContent] = useState<ArticleRequest>({
    title: '',
    url: '',
    image: '',
  });

  const history = useHistory();

  const { postArticle } = usePostArticles();

  const { postMetaOg } = usePostMetaOg();

  const onArticleTitleChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, title: e.target.value });
  };

  const onArticleLinkChanged = async (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, url: e.target.value });
  };

  const onLink = async () => {
    const linkValidation = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;

    if (!linkValidation.test(articleContent.url)) {
      alert('홈페이지 링크가 잘못 입력되었습니다.');
      return;
    }

    const response = await postMetaOg(articleContent.url);

    setisButton(true);

    if (response) {
      setArticleContent({
        ...articleContent,
        title: response.data.title,
        image: response.data.image,
      });
      setIsValidate(false);
    } else {
      const isDefault = window.confirm(
        '게실글에서 제목과 썸네일을 가져오는데 실패했습니다. 기본값을 사용하시겠습니까?'
      );
      if (isDefault) {
        setArticleContent({
          ...articleContent,
          title: '제목을 적어주세요.',
          image:
            'https://user-images.githubusercontent.com/59258239/133797281-819ab585-4da3-4703-9d22-4453d30f9d1f.png',
        });
      }
    }
  };

  const createArticle = async () => {
    postArticle(articleContent);
    history.push(PATH.ARTICLE);
  };

  return (
    <ArticlePageContainer>
      <Title>📑 아티클 작성</Title>
      <InputContainer>
        <Label>링크</Label>
        <Input
          value={articleContent.url}
          placeholder="링크를 입력해주세요."
          onChange={onArticleLinkChanged}
        />
      </InputContainer>
      <Button type="button" size="XX_SMALL" css={[SubmitButtonStyle]} onClick={onLink}>
        링크 입력
      </Button>
      <InputContainer>
        <Label>제목</Label>
        <Input value={articleContent.title} placeholder="제목" onChange={onArticleTitleChanged} />
      </InputContainer>
      <InputContainer>
        <Label>썸네일</Label>
        <Input
          defaultValue={articleContent.image}
          placeholder="이미지 링크"
          disabled={isValidate ? false : true}
        />
        <ThumbnailContainer>
          {articleContent.image && <ThumbnailImage src={articleContent.image} alt="썸네일" />}
        </ThumbnailContainer>
      </InputContainer>
      {isButton && (
        <Button type="button" size="X_SMALL" css={[SubmitButtonStyle]} onClick={createArticle}>
          작성 완료
        </Button>
      )}
    </ArticlePageContainer>
  );
};

export default NewArticlePage;
