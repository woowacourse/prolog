import { css } from '@emotion/react';
import styled from '@emotion/styled';
import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import { Link } from 'react-router-dom';
import LogoImage from '../../assets/images/logo.svg';
import { PATH } from '../../constants';
import GithubLogin from '../GithubLogin/GithubLogin';
import { DropdownMenu } from '../index';
import Button from '../Button/Button';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import NoProfileImage from '../../assets/images/no-profile-image.png';
import SearchIcon from '../../assets/images/search_icon.svg';
import { getProfile } from '../../redux/actions/userAction';

const DropdownToggledStyle = css`
  &:before {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 80;
    display: block;
    cursor: default;
    content: ' ';
    background: transparent;
  }
`;

const Container = styled.div`
  width: 100%;
  height: 6.4rem;
  background-color: #a9cbe5;

  ${({ isDropdownToggled }) => isDropdownToggled && DropdownToggledStyle}
`;

const Wrapper = styled.div`
  position: relative;
  max-width: 112rem;
  padding: 0 4rem;
  height: 100%;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Logo = styled.img`
  height: 3rem;
  ${({ onClick }) => onClick && 'cursor: pointer;'}
`;

const Menu = styled.div`
  display: flex;

  & > *:not(:first-child) {
    margin-left: 1.6rem;
  }
`;

const DropdownLocationStyle = css`
  top: 70px;
  right: 0px;
`;

const searchButtonStyle = css`
  background-color: #ffffff;
  width: 4.8rem;
`;

const whiteBackgroundStyle = css`
  background-color: #ffffff;
`;

const pencilButtonStyle = css`
  width: 4.8rem;
  background-color: #153147;
  color: #ffffff;
`;

const profileButtonStyle = css`
  width: 4.8rem;
`;

const NavBar = () => {
  const history = useHistory();
  const dispatch = useDispatch();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;
  const userError = user.error;

  const [isDropdownToggled, setDropdownToggled] = useState(false);

  useEffect(() => {
    if (accessToken) {
      dispatch(getProfile(accessToken));
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);

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
          <Button size="SMALL" icon={SearchIcon} type="button" css={searchButtonStyle} />
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
                backgroundImageUrl={NoProfileImage}
                onClick={showDropdownMenu}
                css={profileButtonStyle}
              />
              {isDropdownToggled && (
                <DropdownMenu css={DropdownLocationStyle}>
                  <ul>
                    <li>
                      <Link to={PATH.MYPAGE}>
                        <button type="button">마이페이지</button>
                      </Link>
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
