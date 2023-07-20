/** @jsxImportSource @emotion/react */

import Button from '../Button/Button';
import SearchIcon from '../../assets/images/search_icon.svg';
import { Container } from './SearchBar.styles';
import { InterpolationWithTheme } from '@emotion/core';
import { ChangeEventHandler, FormEventHandler } from 'react';
import { Theme } from '@emotion/react';

type SearchBarProps = {
  css?: InterpolationWithTheme<Theme>;
  onSubmit?: FormEventHandler<HTMLFormElement>;
  onChange?: (value: string) => void;
  value: string;
}

const SearchBar = ({ css, onSubmit, onChange, value }: SearchBarProps) => {
  const handleInputValueChange: ChangeEventHandler<HTMLInputElement> = (event) => {
    onChange?.(event.target.value);
  }

  return (
    <form onSubmit={onSubmit}>
      <div css>
      </div>
      <Container css={css}>
        <input type="search" placeholder="검색어를 입력하세요" value={value} onChange={handleInputValueChange} />
        {onSubmit && <Button size="X_SMALL" icon={SearchIcon} type="submit" />}
      </Container>
    </form>
  );
};

export default SearchBar;
