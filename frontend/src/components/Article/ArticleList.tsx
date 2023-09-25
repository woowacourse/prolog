import * as Styled from './ArticleList.style';
import Article from './Article';
import { ArticleType } from '../../models/Article';

interface ArticleListProps {
  articles: ArticleType[];
}

const ArticleList = ({ articles }: ArticleListProps) => {
  return (
    <Styled.Container>
      {articles?.map((article) => (
        <Article key={article.id} {...article} />
      ))}
    </Styled.Container>
  );
};

export default ArticleList;
