import styled from '@emotion/styled';

interface ArticleBookmarkFilterProps {
  checked: boolean;
  handleCheckBookmark: React.ChangeEventHandler<HTMLInputElement>;
}

const ArticleBookmarkFilter = ({ checked, handleCheckBookmark }: ArticleBookmarkFilterProps) => {
  return (
    <ArticleBookmarkFilterContainer>
      <input
        type="checkbox"
        checked={checked}
        onChange={handleCheckBookmark}
        value="북마크한 아티클"
      />
    </ArticleBookmarkFilterContainer>
  );
};

export default ArticleBookmarkFilter;

const ArticleBookmarkFilterContainer = styled.div`
  width: 100px;
  border: 1px solid black;
  border-radius: 5px;
  font-size: 1.5rem;
`;
