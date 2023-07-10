/** @jsxImportSource @emotion/react */

import PropTypes from 'prop-types';
import { useContext, useState } from 'react';
import { Link, NavLink, useHistory } from 'react-router-dom';
import LogoImage from '../../assets/images/logo.svg';
import NoProfileImage from '../../assets/images/no-profile-image.png';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import MobileLogo from '../../assets/images/woteco-logo.png';
import { APP_MODE, isProd } from '../../configs/environment';
import { PATH } from '../../constants';
import { UserContext } from '../../contexts/UserProvider';
import useScreenMediaQuery from '../../hooks/useScreenMediaQuery';
import Button from '../Button/Button';
import GithubLogin from '../GithubLogin/GithubLogin';
import { DropdownMenu } from '../index';
import {
  Container, DropdownStyle, loginButtonStyle, Logo,
  Menu, Navigation, pencilButtonStyle,
  profileButtonStyle, Wrapper
} from './NavBar.styles';


const navigationConfig = [
  {
    path: PATH.ROOT,
    title: '홈',
  },
  {
    path: PATH.STUDYLOG,
    title: '학습로그',
  },
  {
    path: PATH.ROADMAP,
    title: '로드맵',
  },
];

const NavBar = () => {
  const history = useHistory();
  const logoTag = isProd ? 'BETA' : APP_MODE;

  const { user, onLogout } = useContext(UserContext);

  const { username, imageUrl: userImage = NoProfileImage, isLoggedIn } = user;

  const [isDropdownToggled, setDropdownToggled] = useState(false);
  const [isWritingDropdownToggled, setWritingDropdownToggled] = useState(false);
  const { isXs } = useScreenMediaQuery();

  const goMain = () => {
    history.push(PATH.ROOT);
  };

  const showDropdownMenu = () => {
    setDropdownToggled(true);
  };

  const hideWritingDropdownMenu = (event) => {
    if (event.currentTarget === event.target) {
      setWritingDropdownToggled(false);
    }
  };
  const hideDropdownMenu = (event) => {
    if (event.currentTarget === event.target) {
      setDropdownToggled(false);
    }
  };

  const onSelectMenu = (event) => {
    if (event.target.tagName === 'A') {
      setDropdownToggled(false);
      setWritingDropdownToggled(false);
    }
  };

  return (
    <Container
      isDropdownToggled={isDropdownToggled || isWritingDropdownToggled}
      onClick={(e) => {
        hideDropdownMenu(e);
        hideWritingDropdownMenu(e);
      }}
    >
      <Wrapper>
        <Logo onClick={goMain} role="link" aria-label="프롤로그 홈으로 이동하기">
          <img src={isXs ? MobileLogo : LogoImage} alt="" />
          {!isProd && <span>{logoTag}</span>}
        </Logo>
        <Menu role="menu">
          <Navigation>
            {navigationConfig.map(({ path, title }) => (
              <NavLink
                style={{ whiteSpace: 'nowrap' }}
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
              <Link to={PATH.NEW_STUDYLOG}>
                <Button
                  size="XX_SMALL"
                  icon={PencilIcon}
                  type="button"
                  cssProps={pencilButtonStyle}
                />
              </Link>
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
                        path: `/${username}/studylogs`,
                      },
                      {
                        menu: '내 스크랩',
                        path: `/${username}/scraps`,
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
