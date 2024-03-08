import { css } from '@emotion/react';
import { ChangeEventHandler, FormEventHandler } from 'react';

interface SearchBarProps {
  onSubmit?: FormEventHandler;
  onChange: ChangeEventHandler<HTMLInputElement>;
  css?: ReturnType<typeof css>;
  value: string;
}

const SearchBar = ({ css, onSubmit, onChange, value }: SearchBarProps) => {
  return (
    <div>
    </div>
    // <form onSubmit={onSubmit}>
    //   <Container css={css}>
    //     <input type="search" placeholder="검색어를 입력하세요" value={value} onChange={onChange} />
    //     {onSubmit && <Button size="X_SMALL" icon={SearchIcon} type="submit" />}
    //   </Container>
    // </form>
  );
};

export default SearchBar;
