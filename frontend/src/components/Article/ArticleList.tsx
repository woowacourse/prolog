import * as Styled from './ArticleList.style';
import Article from './Article';
import { useGetRequestArticleQuery } from '../../hooks/queries/article';

const ArticleList = () => {
  const { data: articles, isLoading, isError } = useGetRequestArticleQuery();

  if (isLoading) return <div>loading...</div>;
  if (isError) return <div>error...</div>;

  return (
    <Styled.Container>
      {articles?.map((article) => (
        <Article key={article.id} {...article} />
      ))}
    </Styled.Container>
  );
};

export default ArticleList;
