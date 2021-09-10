import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import LogoImage from '../../assets/images/logo.svg';
import { PATH } from '../../constants';
import GithubLogin from '../GithubLogin/GithubLogin';
import { DropdownMenu } from '../index';
import Button from '../Button/Button';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import NoProfileImage from '../../assets/images/no-profile-image.png';
import { getProfile } from '../../redux/actions/userAction';
import {
  Container,
  Wrapper,
  Logo,
  Menu,
  DropdownStyle,
  whiteBackgroundStyle,
  pencilButtonStyle,
  profileButtonStyle,
} from './NavBar.styles';
import { ERROR_MESSAGE } from '../../constants/message';
import SearchBar from '../SearchBar/SearchBar';

const NavBar = () => {
  const location = useLocation();

  const history = useHistory();
  const dispatch = useDispatch();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const user = useSelector((state) => state.user.profile);

  const isLoggedIn = !!user.data;
  const userError = user.error;

  const [isDropdownToggled, setDropdownToggled] = useState(false);
  const [userImage, setUserImage] = useState(NoProfileImage);

  const [searchKeywords, setSearchKeywords] = useState('');

  useEffect(() => {
    if (accessToken) {
      dispatch(getProfile(accessToken));
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);

  useEffect(() => {
    if (user.data?.imageUrl) {
      setUserImage(user.data?.imageUrl);
    }
  }, [user]);

  const goMainWithReload = () => {
    history.push(PATH.ROOT);
    window.location.reload();
  };

  const goNewPost = async () => {
    const accessToken = localStorage.getItem('accessToken');

    if (!accessToken) {
      alert(ERROR_MESSAGE.LOGIN_DEFAULT);
      window.location.reload();
      return;
    }

    history.push(PATH.NEW_POST);
  };

  const showDropdownMenu = () => {
    setDropdownToggled(true);
  };

  const hideDropdownMenu = (event) => {
    if (event.currentTarget === event.target) {
      setDropdownToggled(false);
    }
  };

  const logout = () => {
    localStorage.setItem('accessToken', '');

    window.location.reload();
  };

  const onSearchKeywordsChange = (event) => {
    setSearchKeywords(event.target.value);
  };

  const onSearch = async (event) => {
    event.preventDefault();

    const query = new URLSearchParams(history.location.search);
    query.set('page', 1);

    if (searchKeywords) {
      query.set('keyword', searchKeywords);
    } else {
      query.delete('keyword');
    }

    history.push(`${PATH.ROOT}?${query.toString()}`);
  };

  if (userError) {
    localStorage.removeItem('accessToken');
  }

  const onSelectMenu = (event) => {
    if (event.target.tagName === 'A') {
      setDropdownToggled(false);
    }
  };

  useEffect(() => {
    const query = new URLSearchParams(history.location.search);

    setSearchKeywords(query.get('keyword') ?? '');
  }, [location.search]);

  return (
    <Container isDropdownToggled={isDropdownToggled} onClick={hideDropdownMenu}>
      <Wrapper>
        <Logo onClick={goMainWithReload} role="link">
          <img src={LogoImage} alt="PROLOG 로고" />
          <span>{process.env.REACT_APP_MODE === 'PROD' ? 'BETA' : process.env.REACT_APP_MODE}</span>
        </Logo>
        <Menu role="menu">
          <SearchBar onSubmit={onSearch} value={searchKeywords} onChange={onSearchKeywordsChange} />
          {isLoggedIn ? (
            <>
              <Button
                size="SMALL"
                icon={PencilIcon}
                type="button"
                onClick={goNewPost}
                css={pencilButtonStyle}
              />
              <Button
                size="SMALL"
                type="button"
                backgroundImageUrl={userImage}
                onClick={showDropdownMenu}
                css={profileButtonStyle}
              />
              {isDropdownToggled && (
                <DropdownMenu css={DropdownStyle}>
                  <ul onClick={onSelectMenu}>
                    {[
                      {
                        menu: '내 프로필',
                        path: `/${user?.data.username}`,
                      },
                      {
                        menu: '내 학습로그',
                        path: `/${user?.data.username}/posts`,
                      },
                    ].map(({ menu, path }) => (
                      <li key={menu}>
                        <Link to={path}>{menu}</Link>
                      </li>
                    ))}
                    <li>
                      <button type="button" onClick={logout}>
                        로그아웃
                      </button>
                    </li>
                  </ul>
                </DropdownMenu>
              )}
            </>
          ) : (
            <GithubLogin>
              <Button size="SMALL" type="button" icon={NoProfileImage} css={whiteBackgroundStyle}>
                로그인
              </Button>
            </GithubLogin>
          )}
        </Menu>
      </Wrapper>
    </Container>
  );
};

NavBar.propTypes = {
  children: PropTypes.node,
};

export default NavBar;
