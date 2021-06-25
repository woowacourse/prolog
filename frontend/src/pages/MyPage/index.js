import React from 'react';
import { useSelector } from 'react-redux';
import {
  Container,
  Profile,
  Image,
  Nickname,
  MenuList,
  MenuItem,
  Role,
  Content,
  Title,
} from './styles';

const MyPage = ({ title, children }) => {
  const me = useSelector((state) => state.user.profile.data);

  return (
    <Container>
      <div>
        <Profile>
          <Image src={me?.imageUrl} alt="프로필 이미지" />
          <Role>{me?.role}</Role>
          <Nickname>{me?.nickname}</Nickname>
        </Profile>
        <MenuList>
          <MenuItem>
            <button>글 관리</button>
          </MenuItem>
          <MenuItem>
            <button>내 정보 수정</button>
          </MenuItem>
        </MenuList>
      </div>
      <Content>
        <Title>{title}</Title>
        {children}
      </Content>
    </Container>
  );
};

export default MyPage;
