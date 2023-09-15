import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  requestGetArticles,
  requestPostArticles,
  requestPutArticleBookmark,
} from '../../apis/articles';
import { ArticleType } from '../../models/Article';
import { ERROR_MESSAGE } from '../../constants';
import { SUCCESS_MESSAGE } from '../../constants/message';

const QUERY_KEY = {
  articles: 'articles',
};

export const useGetRequestArticleQuery = () => {
  return useQuery<ArticleType[]>([QUERY_KEY.articles], async () => {
    const response = await requestGetArticles();

    return response.data;
  });
};

export const usePostArticlesMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(requestPostArticles, {
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.articles]);
      alert(SUCCESS_MESSAGE.CREATE_ARTICLE);
    },
    onError: () => {
      alert(ERROR_MESSAGE.DEFAULT);
    },
  });
};

export const usePutArticleBookmarkMutation = () => {
  return useMutation(requestPutArticleBookmark, {
    onSuccess: () => {},
    onError: () => {},
  });
};
