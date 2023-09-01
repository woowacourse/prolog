/** @jsxImportSource @emotion/react */

import ArticleList from '../../components/Article/ArticleList';
import { Button } from '../../components';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';
import styled from '@emotion/styled';
import { MainContentStyle } from '../../PageRouter';
import { useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';

const ArticleListPage = () => {
  const history = useHistory();

  const { user } = useContext(UserContext);
  const { isLoggedIn } = user;

  const goNewArticlePage = () => history.push(PATH.NEW_ARTICLE);

  return (
    <div css={[MainContentStyle]}>
      <Container>
        {isLoggedIn && (
          <Button
            type="button"
            size="SMALL"
            icon={PencilIcon}
            alt="새 아티클 쓰기 아이콘"
            onClick={goNewArticlePage}
          >
            글쓰기
          </Button>
        )}
      </Container>
      <ArticleList />
    </div>
  );
};

export default ArticleListPage;

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-bottom: 20px;
`;
