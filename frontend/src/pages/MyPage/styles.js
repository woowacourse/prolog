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

const MyPostList = styled.div`
  width: 100%;
  margin-left: 3.2rem;
  background-color: #fff;
  border: 1px solid #e6e6e6;
  border-radius: 1.6rem;
  padding: 2.4rem;
`;

const SectionTitle = styled.div`
  font-size: 2.8rem;
  padding-bottom: 1.6rem;
  font-weight: 500;
`;

const PostItem = styled.div`
  height: 18rem;
  border-top: 1px solid #e6e6e6;
  padding: 2.4rem 1.6rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;

  &:hover {
    background-color: #f9f9f9;
  }
`;

const Content = styled.div`
  display: flex;
  height: 100%;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const Mission = styled.div`
  font-size: 1.6rem;
  color: #383838;
`;

const Title = styled.h3`
  font-size: 2.8rem;

  color: #383838;
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: #848484;
  margin-top: auto;
`;

const ButtonList = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1.6rem;
`;

export {
  Container,
  Profile,
  Image,
  Nickname,
  Role,
  MenuList,
  MenuItem,
  MyPostList,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  PostItem,
  SectionTitle,
  ButtonList,
};
