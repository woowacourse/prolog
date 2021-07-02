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
  height: fit-content;
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

const RightSection = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-left: 3.2rem;
  background-color: #fff;
  border-radius: 1.6rem;
  border: 1px solid #e6e6e6;
`;

const MenuList = styled.ul`
  display: flex;
  background-color: #dfecf5;
  border-top-right-radius: 1.6rem;
  border-top-left-radius: 1.6rem;
`;

const MenuItem = styled.li`
  height: 4.8rem;
  display: flex;
  align-items: center;
  padding: 2rem;
  border-top-right-radius: 1.6rem;
  border-top-left-radius: 1.6rem;

  ${({ isSelectedMenu }) => isSelectedMenu && 'background-color: #fff'};
`;

const MenuButton = styled.button`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  font-size: 1.6rem;
  color: #333;
`;

const Content = styled.div`
  width: 100%;
  background-color: #fff;
  border-bottom-left-radius: 1.6rem;
  border-bottom-right-radius: 1.6rem;
  padding: 2.4rem;
  display: flex;
  justify-content: center;
`;

const Title = styled.div`
  font-size: 2.8rem;
  font-weight: 500;
`;

const MenuIcon = styled.img`
  width: 1.6rem;
`;

export {
  Container,
  Profile,
  Image,
  Nickname,
  Role,
  RightSection,
  MenuList,
  MenuItem,
  MenuButton,
  Content,
  Title,
  MenuIcon,
};
