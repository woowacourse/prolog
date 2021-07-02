import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import postIcon from '../../assets/images/post.png';
import overviewIcon from '../../assets/images/overview.png';
import waitImage from '../../assets/images/wait.png';
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
  Preparing,
} from './styles';
import { MYPAGE_MENU } from '../../constants';

const MyPage = ({ children, menu }) => {
  const history = useHistory();
  const { username } = useParams();
  const me = useSelector((state) => state.user.profile.data);

  const [selectedMenu, setSelectedMenu] = useState('');

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

  useEffect(() => {
    setSelectedMenu(menu);
  }, []);

  return (
    <Container>
      <Profile>
        <Image src={me?.imageUrl} alt="프로필 이미지" />
        <Role>{me?.role}</Role>
        <Nickname>{me?.nickname}</Nickname>
      </Profile>
      <RightSection>
        <MenuList>
          <MenuItem isSelectedMenu={selectedMenu === MYPAGE_MENU.OVERVIEW}>
            <MenuButton value={MYPAGE_MENU.OVERVIEW} type="button" onClick={goMyPage}>
              <MenuIcon src={overviewIcon} alt="overview icon" />
              Overview
            </MenuButton>
          </MenuItem>
          <MenuItem isSelectedMenu={selectedMenu === MYPAGE_MENU.POSTS}>
            <MenuButton value={MYPAGE_MENU.POSTS} type="button" onClick={goMyPagePosts}>
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
        <Content>
          {children ? (
            children
          ) : (
            <Preparing>
              <img src={waitImage} alt="준비중 이미지" />
              <div>준비 중이애오. 조금만 기다려 주새오.</div>
            </Preparing>
          )}
        </Content>
      </RightSection>
    </Container>
  );
};

export default MyPage;
