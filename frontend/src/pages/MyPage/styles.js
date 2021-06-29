import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
`;

const Profile = styled.div`
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 1.6rem;
  border: 1px solid #e6e6e6;
  padding-bottom: 1.8rem;
`;

const Image = styled.img`
  width: 20rem;
  height: 20rem;
  border-top-left-radius: 1.6rem;
  border-top-right-radius: 1.6rem;
`;

const Nickname = styled.div`
  display: flex;
  align-items: center;
  font-size: 1.8rem;
  border-top: none;
  padding-left: 1.2rem;
`;

const Role = styled.div`
  margin-top: 1rem;
  padding-left: 1.2rem;
  font-size: 1.2rem;
  color: #888888;
`;

const MenuList = styled.ul`
  background-color: #fff;
  border-radius: 1.6rem;
  padding: 1rem 1.6rem;
  margin-top: 2.4rem;
  border: 1px solid #e6e6e6;
`;

const MenuItem = styled.li`
  height: 4.4rem;

  display: flex;
  align-items: center;

  &:not(:last-child) {
    border-bottom: 1px solid #e6e6e6;
  }
`;

const Content = styled.div`
  width: 100%;
  height: fit-content;
  margin-left: 3.2rem;
  background-color: #fff;
  border: 1px solid #e6e6e6;
  border-radius: 1.6rem;
  padding: 2.4rem;
`;

const Title = styled.div`
  font-size: 2.8rem;
  padding-bottom: 1.6rem;
  font-weight: 500;
  border-bottom: 1px solid #e6e6e6;
`;

export { Container, Profile, Image, Nickname, Role, MenuList, MenuItem, Content, Title };
