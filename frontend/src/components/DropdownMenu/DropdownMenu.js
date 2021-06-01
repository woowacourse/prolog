import styled from '@emotion/styled';
import PropTypes from 'prop-types';

const Container = styled.div`
  min-width: 17.5rem;
  min-height: 5rem;
  background-color: #ffffff;
  border-radius: 2rem;
  box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.3);
  padding: 1rem 1.2rem;
  position: absolute;
  z-index: 100;

  /* transform: translateY(30%); */

  && {
    ${(props) => props.css}
  }

  /* &:before {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: -1;
    content: ' ';
    background-color: rgba(0, 0, 0, 0.4);
  } */

  /* 삼각형 입니다 ^^
    &:before {
    content: '';
    position: absolute;
    border-style: solid;
    border-width: 0 14px 15px;
    border-color: #ffffff transparent;
    display: block;
    width: 0;
    z-index: 0;
    top: -14px;
    right: 16px;
  }

  &:after {
    content: '';
    position: absolute;
    border-style: solid;
    border-width: 0 14px 15px;
    border-color: #ffffff transparent;
    display: block;
    width: 0;
    z-index: 0;
    top: -14px;
    right: 16px;
  } */

  & li {
    height: 5rem;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;

    & > * {
      width: 100%;
      font-size: 1.4rem;
      font-weight: 500;
      color: #333;
      transition: font-size 0.1s ease;
    }
  }

  & li:not(:last-child) {
    border-bottom: 1px solid #aaa;
  }

  /* & li:hover {
    & > * {
      font-size: 2.2rem;
    }
  } */
`;

const DropdownMenu = ({ children, css }) => {
  return <Container css={css}>{children}</Container>;
};

DropdownMenu.propTypes = {
  children: PropTypes.node,
};

export default DropdownMenu;
