import { css } from '@emotion/react';
import styled from '@emotion/styled';
import PropTypes from 'prop-types';
import { useState } from 'react';
import { useHistory } from 'react-router';
import LogoImage from '../../assets/images/logo.svg';
import { PATH } from '../../constants';
import { DropdownMenu } from '../index';

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
  max-width: 128rem;
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
  & > button:not(:last-child) {
    margin-right: 1.6rem;
  }
`;

const DropdownLocationStyle = css`
  top: 60px;
  right: 0px;
`;

const NavBar = () => {
  const history = useHistory();
  const [isDropdownToggled, setDropdownToggled] = useState(false);

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

  return (
    <Container isDropdownToggled={isDropdownToggled} onClick={hideDropdownMenu}>
      <Wrapper>
        <Logo src={LogoImage} alt="STUDYLOG 로고" onClick={goMain} role="link" />
        <Menu role="menu">
          <button type="button">검색</button>
          <button type="button" onClick={goNewPost}>
            글작성
          </button>
          <button type="button" onClick={showDropdownMenu}>
            내정보
          </button>
        </Menu>
        {isDropdownToggled && (
          <DropdownMenu css={DropdownLocationStyle}>
            <ul>
              <li>
                <button type="button">마이페이지</button>
              </li>
              <li>
                <button type="button">로그아웃</button>
              </li>
            </ul>
          </DropdownMenu>
        )}
      </Wrapper>
    </Container>
  );
};

NavBar.propTypes = {
  children: PropTypes.node,
};

export default NavBar;
