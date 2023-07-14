import { useContext, useEffect, useState } from 'react';
import { requestGetArticles, requestPostArticles } from '../../apis/articles';
import { UserContext } from '../../contexts/UserProvider';

export const useFetchArticles = async () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const [articles, setArticles] = useState();

  const fetchArticles = async () => {
    const response = await requestGetArticles({ accessToken });
    setArticles(response.data);
  };

  useEffect(() => {
    fetchArticles();
  }, []);

  return { articles };
};

export const usePostArticles = () => {
  const { user } = useContext(UserContext);
  const { accessToken } = user;

  const postArticle = async (body) => {
    await requestPostArticles({ accessToken, body });
  };

  return { postArticle };
};