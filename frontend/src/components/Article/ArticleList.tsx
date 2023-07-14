import * as Styled from './ArticleList.style';
import Article from './Article';
import { useFetchArticles } from '../../hooks/Articles/useArticles';

const ArticleList = () => {
  const { articles } = useFetchArticles();

  return (
    <Styled.Container>
      {articles.map((article) => (
        <Article key={article.id} {...article} />
      ))}
    </Styled.Container>
  );
};

export default ArticleList;
