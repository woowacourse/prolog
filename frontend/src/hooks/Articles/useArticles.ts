import { useEffect, useState } from 'react';
import { MetaOgRequest, ArticleRequest, ArticleType } from '../../models/Article';
import { requestGetArticles, requestPostArticles, requestGetMetaOg } from '../../apis/articles';

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

export const useGetMetaOg = () => {
  const getMetaOg = async (url: MetaOgRequest) => {
    const data = await requestGetMetaOg(url);

    return data;
  };

  return { getMetaOg };
};
