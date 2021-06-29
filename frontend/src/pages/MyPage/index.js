import React from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';
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
  const history = useHistory();

  const me = useSelector((state) => state.user.profile.data);

  const goMyPagePosts = () => {
    history.push(PATH.MYPAGE_POSTS);
  };

  const goMyPageAccount = () => {
    history.push(PATH.MYPAGE_ACCOUNT);
  };

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
            <button type="button" onClick={goMyPagePosts}>
              글 관리
            </button>
          </MenuItem>
          {/* <MenuItem>
            <button type="button" onClick={goMyPageAccount}>
              내 정보 수정
            </button>
          </MenuItem> */}
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
