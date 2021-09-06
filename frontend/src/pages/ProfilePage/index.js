import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import { ReactComponent as PostIcon } from '../../assets/images/post.svg';
import { ReactComponent as OverviewIcon } from '../../assets/images/overview.svg';
import waitImage from '../../assets/images/wait.png';
import {
  Container,
  Profile,
  Image,
  Nickname,
  MenuList,
  MenuItem,
  MenuButton,
  Role,
  Content,
  Preparing,
  SideBar,
} from './styles';
import { PROFILE_PAGE_MENU } from '../../constants';
import { requestGetProfile } from '../../service/requests';
import useNotFound from '../../hooks/useNotFound';

const ProfilePage = ({ children, menu }) => {
  const history = useHistory();
  const { username } = useParams();

  const [user, setUser] = useState({});
  const [selectedMenu, setSelectedMenu] = useState(menu);

  const { isNotFound, setNotFound, NotFound } = useNotFound();

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
        throw new Error(await response.text());
      }

      setUser(await response.json());
      setNotFound(false);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);

      if (errorResponse.code === 1004) {
        setNotFound(true);
      }
    }
  };

  useEffect(() => {
    getProfile();
    setSelectedMenu(menu);
  }, [username]);

  useEffect(() => {
    setSelectedMenu(menu);
  }, [menu]);

  if (isNotFound) {
    return <NotFound />;
  }

  return (
    <Container>
      <SideBar>
        <Profile>
          <Image src={user?.imageUrl} alt="프로필 이미지" />
          <Role>{user?.role}</Role>
          <Nickname>{user?.nickname}</Nickname>
        </Profile>
        <MenuList>
          <MenuItem isSelectedMenu={selectedMenu === PROFILE_PAGE_MENU.OVERVIEW}>
            <MenuButton value={PROFILE_PAGE_MENU.OVERVIEW} type="button" onClick={goProfilePage}>
              {/* <MenuIcon src={overviewIcon} alt="overview icon" /> */}
              <OverviewIcon width="16" height="16" />
              관리 홈
            </MenuButton>
          </MenuItem>
          <MenuItem isSelectedMenu={selectedMenu === PROFILE_PAGE_MENU.POSTS}>
            <MenuButton value={PROFILE_PAGE_MENU.POSTS} type="button" onClick={goProfilePagePosts}>
              <PostIcon width="16" height="16" />
              학습로그
            </MenuButton>
          </MenuItem>
          <MenuItem isSelectedMenu={selectedMenu === 'asd'}>
            <MenuButton value={PROFILE_PAGE_MENU.POSTS} type="button" onClick={goProfilePagePosts}>
              <PostIcon width="16" height="16" />
              리포트
            </MenuButton>
          </MenuItem>
          {/* <MenuItem>
            <button type="button" onClick={goProfilePageAccount}>
              내 정보 수정
            </button>
          </MenuItem> */}
        </MenuList>
      </SideBar>
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
    </Container>
  );
};

export default ProfilePage;
