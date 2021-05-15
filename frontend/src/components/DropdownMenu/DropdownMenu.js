import styled from '@emotion/styled';
import PropTypes from 'prop-types';

const Container = styled.div`
  width: 17.5rem;
  min-height: 5rem;
  background-color: #ffffff;
  border-radius: 2rem;
  box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.3);
  padding: 0% 1.2rem;
  position: absolute;

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
  }

  & li {
    height: 5rem;
    text-align: center;
    display: flex;
    justify-content: center;

    & > * {
      width: 100%;
      font-size: 2rem;
      font-weight: 600;
      color: #153147;
      transition: font-size 0.1s ease;
    }
  }

  & li:not(:last-child) {
    border-bottom: 1px solid #707070;
  }

  & li:hover {
    & > * {
      font-size: 2.4rem;
    }
  }
`;

const DropdwonMenu = ({ children }) => {
  return <Container>{children}</Container>;
};

DropdwonMenu.proptyes = {
  children: PropTypes.node,
};

export default DropdwonMenu;
