import Button from '../Button/Button';
import SearchIcon from '../../assets/images/search_icon.svg';
import { Container } from './SearchBar.styles';

const SearchBar = ({ onSubmit, onChange, value }) => {
  return (
    <form onSubmit={onSubmit}>
      <Container>
        <input type="search" placeholder="검색어를 입력하세요" value={value} onChange={onChange} />
        <Button size="X_SMALL" icon={SearchIcon} type="submit" />
      </Container>
    </form>
  );
};

export default SearchBar;
