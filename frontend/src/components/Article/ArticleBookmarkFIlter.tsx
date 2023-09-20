import styled from '@emotion/styled';

interface ArticleBookmarkFilterProps {
  checked: boolean;
  handleCheckBookmark: React.ChangeEventHandler<HTMLInputElement>;
}

const ArticleBookmarkFilter = ({ checked, handleCheckBookmark }: ArticleBookmarkFilterProps) => {
  return (
    <ArticleBookmarkFilterContainer>
      <label>
        <input type="checkbox" checked={checked} onChange={handleCheckBookmark} />
        북마크한 아티클
      </label>
    </ArticleBookmarkFilterContainer>
  );
};

export default ArticleBookmarkFilter;

const ArticleBookmarkFilterContainer = styled.div`
  display: flex;
  align-items: center;
  margin-left: 10px;
  width: 150px;
  height: 100%;
  font-size: 1.5rem;
`;
