import { useEffect, useState } from 'react';
import { ArticleMetaOgRequest, ArticleRequest, ArticleType } from '../../models/Article';
import { requestGetArticles, requestPostArticles, requestPostMetaOg } from '../../apis/articles';

export const useFetchArticles = () => {
  const [articles, setArticles] = useState<ArticleType[]>([]);

  const fetchArticles = async () => {
    const response = await requestGetArticles();
    setArticles(response.data);
  };

  useEffect(() => {
    fetchArticles();
  }, []);

  return { articles };
};

export const usePostArticles = () => {
  const postArticle = async (articleRequest: ArticleRequest) => {
    await requestPostArticles(articleRequest);
  };

  return { postArticle };
};

export const usePostMetaOg = () => {
  const postMetaOg = async (body: ArticleMetaOgRequest) => {
    const data = await requestPostMetaOg(body);
    return data;
  };

  return { postMetaOg };
};
