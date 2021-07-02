import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import postIcon from '../../assets/images/post.png';
import overviewIcon from '../../assets/images/overview.png';
import {
  Container,
  Profile,
  Image,
  Nickname,
  RightSection,
  MenuList,
  MenuItem,
  MenuIcon,
  MenuButton,
  Role,
  Content,
} from './styles';

const MyPage = ({ children }) => {
  const history = useHistory();
  const { username } = useParams();
  const me = useSelector((state) => state.user.profile.data);

  const [selectedMenu, setSelectedMenu] = useState('overview');

  const goMyPage = (event) => {
    setSelectedMenu(event.currentTarget.value);
    history.push(`/${username}`);
  };

  const goMyPagePosts = (event) => {
    setSelectedMenu(event.currentTarget.value);
    history.push(`/${username}/posts`);
  };

  const goMyPageAccount = () => {
    history.push(`/${username}/account`);
  };

  return (
    <Container>
      <Profile>
        <Image src={me?.imageUrl} alt="프로필 이미지" />
        <Role>{me?.role}</Role>
        <Nickname>{me?.nickname}</Nickname>
      </Profile>
      <RightSection>
        <MenuList>
          <MenuItem isSelectedMenu={selectedMenu === 'overview'}>
            <MenuButton value="overview" type="button" onClick={goMyPage}>
              <MenuIcon src={overviewIcon} />
              Overview
            </MenuButton>
          </MenuItem>
          <MenuItem isSelectedMenu={selectedMenu === 'posts'}>
            <MenuButton value="posts" type="button" onClick={goMyPagePosts}>
              <MenuIcon src={postIcon} alt="posts icon" />
              Posts
            </MenuButton>
          </MenuItem>
          {/* <MenuItem>
            <button type="button" onClick={goMyPageAccount}>
              내 정보 수정
            </button>
          </MenuItem> */}
        </MenuList>
        <Content>{children ? children : <div>준비중입니다. 조금만 기다려주세요 🤪</div>}</Content>
      </RightSection>
    </Container>
  );
};

export default MyPage;
