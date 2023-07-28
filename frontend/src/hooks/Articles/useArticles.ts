import { useEffect, useState } from 'react';
import { requestGetArticles, requestPostArticles } from '../../apis/articles';
import { ArticleRequest, ArticleType } from '../../models/Article';

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
