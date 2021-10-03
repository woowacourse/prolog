import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useHistory } from 'react-router-dom';
import { ReactComponent as PostIcon } from '../../assets/images/post.svg';
import { ReactComponent as OverviewIcon } from '../../assets/images/overview.svg';
import {
  Profile,
  Image,
  Nickname,
  MenuList,
  MenuItem,
  MenuButton,
  Role,
  Container,
  EditButtonStyle,
  NicknameWrapper,
  NicknameInput,
} from './ProfilePageSideBar.styles';
import { PROFILE_PAGE_MENU } from '../../constants';
import { requestEditProfile, requestGetProfile } from '../../service/requests';
import useNotFound from '../../hooks/useNotFound';
import { Button, BUTTON_SIZE } from '..';
import { useSelector } from 'react-redux';

const getMenuList = ({ username, isOwner }) => {
  const defaultMenu = [
    {
      key: PROFILE_PAGE_MENU.OVERVIEW,
      title: '오버뷰',
      path: `/${username}`,
      Icon: OverviewIcon,
    },
    {
      key: PROFILE_PAGE_MENU.POSTS,
      title: '학습로그',
      path: `/${username}/posts`,
      Icon: PostIcon,
    },
    {
      key: PROFILE_PAGE_MENU.REPORTS,
      title: '리포트',
      path: `/${username}/reports`,
      Icon: PostIcon,
    },
  ];
  const abilityMenu = {
    key: PROFILE_PAGE_MENU.ABILITY,
    title: '역량',
    path: `/${username}/ability`,
    Icon: PostIcon,
  };

  return isOwner ? [...defaultMenu, abilityMenu] : defaultMenu;
};

const ProfilePageSideBar = ({ menu }) => {
  const history = useHistory();
  const { username } = useParams();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const myName = useSelector((state) => state.user.profile.data?.username);

  const [user, setUser] = useState({});
  const [selectedMenu, setSelectedMenu] = useState('');

  const [isProfileEditing, setIsProfileEditing] = useState(false);
  const [nickname, setNickname] = useState('');

  const { setNotFound } = useNotFound();

  const loginUser = useSelector((state) => state.user.profile);
  const isOwner = !!loginUser.data && username === loginUser.data.username;

  const getProfile = async () => {
    try {
      const response = await requestGetProfile(username);

      if (!response.ok) {
        throw new Error(await response.text());
      }

      const user = await response.json();
      console.log(user);
      setUser(user);
      setNickname(user.nickname);
      setNotFound(false);
    } catch (error) {
      const errorResponse = JSON.parse(error.message);

      console.error(errorResponse);

      if (errorResponse.code === 1004) {
        setNotFound(true);
      }
    }
  };

  const editProfile = async () => {
    if (nickname === user.nickname) return setIsProfileEditing(false);

    if (nickname.length > 4) {
      alert('닉네임은 4글자 이하로 입력해주세요.');

      return;
    }

    try {
      const response = await requestEditProfile(
        { username, nickname, imageUrl: user.imageUrl },
        accessToken
      );

      if (!response.ok) {
        throw new Error(await response.text());
      }

      setUser((prevValue) => ({ ...prevValue, nickname }));
      setNickname(nickname);
      setIsProfileEditing(false);
    } catch (error) {
      console.error(error.message);
      alert('회원 정보 수정에 실패했습니다.');
    }
  };

  useEffect(() => {
    getProfile();
    setSelectedMenu(menu);
  }, [username]);

  return (
    <Container>
      <Profile>
        <Image src={user?.imageUrl} alt="프로필 이미지" /> <Role>{user?.role}</Role>
        <NicknameWrapper>
          {isProfileEditing ? (
            <NicknameInput
              autoFocus
              value={nickname}
              onChange={({ target }) => setNickname(target.value)}
            />
          ) : (
            <Nickname>{user?.nickname}</Nickname>
          )}
          {myName === username && (
            <Button
              size={BUTTON_SIZE.X_SMALL}
              type="button"
              css={EditButtonStyle}
              alt={isProfileEditing ? '수정 완료 버튼' : '수정 버튼'}
              onClick={() => {
                isProfileEditing ? editProfile() : setIsProfileEditing(true);
              }}
            >
              {isProfileEditing ? '완료' : '수정'}
            </Button>
          )}
        </NicknameWrapper>
      </Profile>
      <MenuList>
        {getMenuList({ username, isOwner }).map((menuItem) => (
          <MenuItem key={menuItem.key} isSelectedMenu={selectedMenu === menuItem.key}>
            <MenuButton
              type="button"
              onClick={() => {
                setSelectedMenu(menuItem.key);
                history.push(menuItem.path);
              }}
            >
              <menuItem.Icon width="16" height="16" />
              {menuItem.title}
            </MenuButton>
          </MenuItem>
        ))}
      </MenuList>
    </Container>
  );
};

export default ProfilePageSideBar;
