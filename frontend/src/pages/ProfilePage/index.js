import React, { useState, useEffect } from 'react';
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
import { PROFILE_PAGE_MENU } from '../../constants';
import { requestGetProfile } from '../../service/requests';

const ProfilePage = ({ children, menu }) => {
  const history = useHistory();
  const { username } = useParams();

  const [user, setUser] = useState({});
  const [selectedMenu, setSelectedMenu] = useState('');

  const goProfilePage = (event) => {
    setSelectedMenu(event.currentTarget.value);
    history.push(`/${username}`);
  };

  const goProfilePagePosts = (event) => {
    setSelectedMenu(event.currentTarget.value);
    history.push(`/${username}/posts`);
  };

  const goProfilePageAccount = () => {
    history.push(`/${username}/account`);
  };

  const getProfile = async () => {
    try {
      const response = await requestGetProfile(username);

      if (!response.ok) {
        throw new Error();
      }

      setUser(await response.json());
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    getProfile();
    setSelectedMenu(menu);
  }, [username]);

  useEffect(() => {
    setSelectedMenu(menu);
  }, []);

  return (
    <Container>
      <Profile>
        <Image src={user?.imageUrl} alt="프로필 이미지" />
        <Role>{user?.role}</Role>
        <Nickname>{user?.nickname}</Nickname>
      </Profile>
      <RightSection>
        <MenuList>
          <MenuItem isSelectedMenu={selectedMenu === PROFILE_PAGE_MENU.OVERVIEW}>
            <MenuButton value={PROFILE_PAGE_MENU.OVERVIEW} type="button" onClick={goProfilePage}>
              <MenuIcon src={overviewIcon} alt="overview icon" />
              Overview
            </MenuButton>
          </MenuItem>
          <MenuItem isSelectedMenu={selectedMenu === PROFILE_PAGE_MENU.POSTS}>
            <MenuButton value={PROFILE_PAGE_MENU.POSTS} type="button" onClick={goProfilePagePosts}>
              <MenuIcon src={postIcon} alt="posts icon" />
              Posts
            </MenuButton>
          </MenuItem>
          {/* <MenuItem>
            <button type="button" onClick={goProfilePageAccount}>
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

export default ProfilePage;
