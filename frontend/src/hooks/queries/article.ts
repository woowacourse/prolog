import { useMutation, useQuery, useQueryClient } from 'react-query';
import {
  requestGetFilteredArticle,
  requestPostArticleView,
  requestPostArticles,
  requestPutArticleBookmark,
  requestPutArticleLike,
} from '../../apis/articles';
import { ArticleType } from '../../models/Article';
import { ERROR_MESSAGE } from '../../constants';
import { SUCCESS_MESSAGE } from '../../constants/message';

const QUERY_KEY = {
  filteredArticles: 'filteredArticles',
};

export const useGetFilteredArticleQuery = (course: string, bookmark: boolean) => {
  return useQuery<ArticleType[]>([QUERY_KEY.filteredArticles], async () => {
    const response = await requestGetFilteredArticle(course, bookmark);

    return response.data;
  });
};

export const usePostArticlesMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(requestPostArticles, {
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.filteredArticles]);
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

export const usePutArticleLikeMutation = () => {
  return useMutation(requestPutArticleLike, {
    onSuccess: () => {},
    onError: () => {},
  });
};

export const usePostArticleViewsMutation = () => {
  return useMutation(requestPostArticleView, {
    onSuccess: () => {
      alert(SUCCESS_MESSAGE.CREATE_ARTICLE);
    },
    onError: () => {
      alert(ERROR_MESSAGE.DEFAULT);
    },
  });
};
