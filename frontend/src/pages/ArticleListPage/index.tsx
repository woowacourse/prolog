/** @jsxImportSource @emotion/react */

import ArticleList from '../../components/Article/ArticleList';
import { Button } from '../../components';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import { useHistory } from 'react-router-dom';
import { COLOR, PATH } from '../../constants';
import styled from '@emotion/styled';
import { MainContentStyle } from '../../PageRouter';
import SelectBox from '../../components/Controls/SelectBox';
import { useEffect, useState } from 'react';
import { css } from '@emotion/react';
import ArticleBookmarkFilter from '../../components/Article/ArticleBookmarkFIlter';
import { useGetFilteredArticleQuery } from '../../hooks/queries/article';

const CATEGORY_OPTIONS = [
  { value: '전체보기', label: '전체보기' },
  { value: '프론트엔드', label: '프론트엔드' },
  { value: '백엔드', label: '백엔드' },
  { value: '안드로이드', label: '안드로이드' },
];

type CategoryOptions = typeof CATEGORY_OPTIONS[number];

const ArticleListPage = () => {
  const history = useHistory();
  const goNewArticlePage = () => history.push(PATH.NEW_ARTICLE);
  const [selectedCourse, setSelectedCourse] = useState<CategoryOptions>(CATEGORY_OPTIONS[0]);
  const [checked, setChecked] = useState(false);

  const { data: filteredArticles = [], refetch: getFilteredArticles } = useGetFilteredArticleQuery(
    selectedCourse.value,
    checked
  );

  const changeFilterOption: (option: { value: string; label: string }) => void = (option) => {
    setSelectedCourse(option);
  };

  const handleCheckBookmark: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setChecked(e.currentTarget.checked);
  };

  useEffect(() => {
    getFilteredArticles();
  }, [checked, selectedCourse]);

  return (
    <div css={[MainContentStyle]}>
      <Container>
        <FilteringWrapper>
          <SelectBoxWrapper>
            <SelectBox
              isClearable={false}
              value={selectedCourse}
              defaultOption={selectedCourse}
              options={CATEGORY_OPTIONS}
              onChange={changeFilterOption}
            />
          </SelectBoxWrapper>
          <ArticleBookmarkFilter checked={checked} handleCheckBookmark={handleCheckBookmark} />
        </FilteringWrapper>
        <Button
          type="button"
          size="X_SMALL"
          icon={PencilIcon}
          alt="새 아티클 쓰기 아이콘"
          onClick={goNewArticlePage}
          cssProps={WriteButtonStyle}
        >
          글쓰기
        </Button>
      </Container>
      <ArticleList articles={filteredArticles} />
    </div>
  );
};

export default ArticleListPage;

export const Container = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 20px;
  gap: 15px;
`;

const FilteringWrapper = styled.div`
  display: flex;
`;

const SelectBoxWrapper = styled.div`
  width: 150px;
`;

export const WriteButtonStyle = css`
  width: 100px;
  height: 42px;
  padding: 0.2rem 0.8rem;

  border-radius: 0.6rem;
  font-size: 1.6rem;

  color: ${COLOR.WHITE};

  :hover {
    background-color: ${COLOR.DARK_BLUE_600};
  }
`;
