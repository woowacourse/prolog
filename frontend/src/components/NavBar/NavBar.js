import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import LogoImage from '../../assets/images/logo.svg';
import { PATH } from '../../constants';
import GithubLogin from '../GithubLogin/GithubLogin';
import { DropdownMenu } from '../index';
import Button from '../Button/Button';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import NoProfileImage from '../../assets/images/no-profile-image.png';
import SearchIcon from '../../assets/images/search_icon.svg';
import { getProfile } from '../../redux/actions/userAction';
import {
  Container,
  Wrapper,
  Logo,
  Menu,
  DropdownStyle,
  whiteBackgroundStyle,
} from './NavBar.styles';

const NavBar = () => {
  const history = useHistory();
  const dispatch = useDispatch();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;
  const userError = user.error;

  const [isDropdownToggled, setDropdownToggled] = useState(false);
  const [userImage, setUserImage] = useState(NoProfileImage);

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

  const goMain = () => {
    history.push(PATH.ROOT);
  };

  const goNewPost = () => {
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

  if (userError) {
    localStorage.setItem('accessToken', '');
  }

  return (
    <Container isDropdownToggled={isDropdownToggled} onClick={hideDropdownMenu}>
      <Wrapper>
        <Logo src={LogoImage} alt="STUDYLOG 로고" onClick={goMain} role="link" />
        <Menu role="menu">
          <Button size="X_SMALL" icon={SearchIcon} type="button" css={whiteBackgroundStyle} />
          {isLoggedIn ? (
            <>
              <Button size="X_SMALL" icon={PencilIcon} type="button" onClick={goNewPost} />
              <Button
                size="X_SMALL"
                type="button"
                backgroundImageUrl={userImage}
                onClick={showDropdownMenu}
              />
              {isDropdownToggled && (
                <DropdownMenu css={DropdownStyle}>
                  <ul>
                    <li>
                      <button type="button">마이페이지</button>
                    </li>
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
