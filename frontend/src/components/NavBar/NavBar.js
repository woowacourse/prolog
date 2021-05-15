import styled from '@emotion/styled';
import PropTypes from 'prop-types';
import LogoImage from '../../assets/images/logo.svg';

const Container = styled.div`
  width: 100%;
  height: 6.4rem;
  background-color: #a9cbe5;
`;

const Wrapper = styled.div`
  max-width: 128rem;
  height: 100%;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Logo = styled.img`
  height: 4rem;
  ${({ onClick }) => onClick && 'cursor: pointer;'}
`;

const Menu = styled.div`
  & > *:not(:last-child) {
    margin-right: 1.6rem;
  }
`;

const NavBar = ({ children, onLogoClick }) => {
  return (
    <Container>
      <Wrapper>
        <Logo
          src={LogoImage}
          alt="STUDYLOG 로고"
          onClick={onLogoClick}
          role={onLogoClick && 'button'}
        />
        <Menu role="menu">{children}</Menu>
      </Wrapper>
    </Container>
  );
};

NavBar.proptyes = {
  children: PropTypes.node,
  onLogoClick: PropTypes.func,
};

export default NavBar;
