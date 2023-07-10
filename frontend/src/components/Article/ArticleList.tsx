import * as Styled from './ArticleList.style';
import Article from './Article';
import type { ArticleType } from '../../models/Article';

const mockArticle: ArticleType = {
  id: 1,
  userName: '패트릭',
  title: 'JS Prototype',
  url: 'https://poiemaweb.com/js-prototype',
  createdAt: '2023-07-10',
};

const mockArticleList = [
  mockArticle,
  mockArticle,
  mockArticle,
  mockArticle,
  mockArticle,
  mockArticle,
  mockArticle,
  mockArticle,
];

const ArticleList = () => {
  return (
    <Styled.Container>
      {mockArticleList.map((article) => (
        <Article key={article.id} {...article} />
      ))}
    </Styled.Container>
  );
};

export default ArticleList;
