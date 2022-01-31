/** @jsxImportSource @emotion/react */

import PropTypes from 'prop-types';
import { useContext, useState } from 'react';
import { useHistory, Link, NavLink } from 'react-router-dom';
import LogoImage from '../../assets/images/logo.svg';
import { PATH } from '../../constants';
import GithubLogin from '../GithubLogin/GithubLogin';
import { DropdownMenu } from '../index';
import Button from '../Button/Button';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import NoProfileImage from '../../assets/images/no-profile-image.png';

import {
  Container,
  Wrapper,
  Logo,
  Menu,
  DropdownStyle,
  pencilButtonStyle,
  profileButtonStyle,
  Navigation,
  loginButtonStyle,
} from './NavBar.styles';
import { ERROR_MESSAGE } from '../../constants/message';
import { UserContext } from '../../contexts/UserProvider';
import { APP_MODE, isProd } from '../../configs/environment';

const navigationConfig = [
  {
    path: PATH.ROOT,
    title: '홈',
  },
  {
    path: PATH.STUDYLOG,
    title: '학습로그',
  },
];

const NavBar = () => {
  const history = useHistory();
  const logoTag = isProd ? 'BETA' : APP_MODE;

  const { user, onLogout } = useContext(UserContext);

  const { username, imageUrl: userImage = NoProfileImage, accessToken, isLoggedIn } = user;

  const [isDropdownToggled, setDropdownToggled] = useState(false);

  const goMain = () => {
    history.push(PATH.ROOT);
  };

  const goNewPost = async () => {
    if (!accessToken) {
      alert(ERROR_MESSAGE.LOGIN_DEFAULT);

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

  const onSelectMenu = (event) => {
    if (event.target.tagName === 'A') {
      setDropdownToggled(false);
    }
  };

  return (
    <Container isDropdownToggled={isDropdownToggled} onClick={hideDropdownMenu}>
      <Wrapper>
        <Logo onClick={goMain} role="link" aria-label="프롤로그 홈으로 이동하기">
          <img src={LogoImage} alt="" />
          <span>{logoTag}</span>
        </Logo>
        <Menu role="menu">
          <Navigation>
            {navigationConfig.map(({ path, title }) => (
              <NavLink
                exact
                key={path}
                to={path}
                activeStyle={{
                  borderBottom: '2px solid black',
                  fontWeight: '600',
                }}
              >
                {title}
              </NavLink>
            ))}
          </Navigation>
          {isLoggedIn ? (
            <>
              <Button
                size="XX_SMALL"
                icon={PencilIcon}
                type="button"
                onClick={goNewPost}
                cssProps={pencilButtonStyle}
              />
              <Button
                size="XX_SMALL"
                type="button"
                backgroundImageUrl={userImage}
                onClick={showDropdownMenu}
                cssProps={profileButtonStyle}
              />
              {isDropdownToggled && (
                <DropdownMenu cssProps={DropdownStyle}>
                  <ul onClick={onSelectMenu}>
                    {[
                      {
                        menu: '내 프로필',
                        path: `/${username}`,
                      },
                      {
                        menu: '내 학습로그',
                        path: `/${username}/posts`,
                      },
                    ].map(({ menu, path }) => (
                      <li key={menu}>
                        <Link to={path}>{menu}</Link>
                      </li>
                    ))}
                    <li>
                      <button type="button" onClick={onLogout}>
                        로그아웃
                      </button>
                    </li>
                  </ul>
                </DropdownMenu>
              )}
            </>
          ) : (
            <GithubLogin>
              <div css={[loginButtonStyle]}>
                <img src={NoProfileImage} alt="" />
                로그인
              </div>
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
