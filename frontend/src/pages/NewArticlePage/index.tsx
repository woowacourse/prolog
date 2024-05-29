import { SyntheticEvent, useState } from 'react';
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

import { useGetMetaOg } from '../../hooks/Articles/useArticles';
import { ArticleRequest } from '../../models/Article';
import { PATH } from '../../constants';
import { usePostArticlesMutation } from '../../hooks/queries/article';
import NotFound from '../../assets/images/not-found.png';

const NewArticlePage = () => {
  const [isValidate, setIsValidate] = useState<boolean>(false);
  const [isButton, setIsButton] = useState<boolean>(false);

  const [articleContent, setArticleContent] = useState<ArticleRequest>({
    title: '',
    description: '',
    url: '',
    imageUrl: '',
  });

  const history = useHistory();

  const { mutate: postArticleMutate } = usePostArticlesMutation();

  const { getMetaOg } = useGetMetaOg();

  const onArticleTitleChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, title: e.target.value });
  };

  const onArticleDescriptionChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, description: e.target.value });
  };

  const onArticleThumbnailChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, imageUrl: e.target.value });
  };

  const onArticleUrlChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setArticleContent({ ...articleContent, url: e.target.value });
  };

  const clickEnterInput = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      onUrl();
    }
  };

  const onUrl = async () => {
    const linkValidation = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;

    if (!linkValidation.test(articleContent.url)) {
      alert('홈페이지 링크가 잘못 입력되었습니다.');
      return;
    }

    const response = await getMetaOg({ url: articleContent.url });

    setIsButton(true);

    if (response.data.title !== '' && response.data.imageUrl !== '') {
      setArticleContent({
        ...articleContent,
        title: response.data.title,
        imageUrl: response.data.imageUrl,
        description: response.data.description
      });
      setIsValidate(true);
    } else if (response.data.title !== '' && response.data.imageUrl === '') {
      setArticleContent({
        ...articleContent,
        title: response.data.title,
        imageUrl:
          'https://user-images.githubusercontent.com/59258239/133797281-819ab585-4da3-4703-9d22-4453d30f9d1f.png',
        description: response.data.description
      });
      setIsValidate(false);
    } else if (response.data.title === '' && response.data.imageUrl !== '') {
      setArticleContent({
        ...articleContent,
        title: '제목을 적어주세요.',
        imageUrl: response.data.imageUrl,
        description: response.data.description
      });
      setIsValidate(true);
    } else {
      setArticleContent({
        ...articleContent,
        title: '제목을 적어주세요.',
        imageUrl:
          'https://user-images.githubusercontent.com/59258239/133797281-819ab585-4da3-4703-9d22-4453d30f9d1f.png',
        description: response.data.description
      });
      setIsValidate(false);
    }
  };

  const addDefaultImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
    e.currentTarget.src = NotFound;
  };

  const createArticle = async () => {
    postArticleMutate(articleContent);
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
          onChange={onArticleUrlChanged}
          onKeyPress={clickEnterInput}
        />
      </InputContainer>
      <Button type="button" size="XX_SMALL" css={[SubmitButtonStyle]} onClick={onUrl}>
        링크 입력
      </Button>
      {isButton && (
        <>
          <InputContainer>
            <Label>제목</Label>
            <Input
              value={articleContent.title}
              placeholder="제목"
              onChange={onArticleTitleChanged}
            />
            <Label>설명</Label>
            <Input
              value={articleContent.description}
              placeholder="설명"
              onChange={onArticleDescriptionChanged}
            />
          </InputContainer>
          <InputContainer>
            <Label>썸네일</Label>
            <Input
              defaultValue={articleContent.imageUrl}
              placeholder="이미지 링크"
              disabled={isValidate ? true : false}
              onChange={onArticleThumbnailChanged}
            />
            <ThumbnailContainer>
              {articleContent.imageUrl && (
                <ThumbnailImage src={articleContent.imageUrl} onError={addDefaultImg} />
              )}
            </ThumbnailContainer>
          </InputContainer>
          <Button type="button" size="X_SMALL" css={[SubmitButtonStyle]} onClick={createArticle}>
            작성 완료
          </Button>
        </>
      )}
    </ArticlePageContainer>
  );
};

export default NewArticlePage;
