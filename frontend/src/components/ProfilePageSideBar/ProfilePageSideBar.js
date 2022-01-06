import { useState, useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';

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

import { requestEditProfile, requestGetProfile } from '../../service/requests';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import { Button, BUTTON_SIZE } from '..';
import { useSelector } from 'react-redux';
import getMenuList from './getMenuList';
import { PROFILE } from '../../constants/input';
import { ALERT_MESSAGE } from '../../constants';

const ProfilePageSideBar = ({ menu }) => {
  const history = useHistory();
  const { username } = useParams();

  const loginUser = useSelector((state) => state.user.profile);
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const isOwner = !!loginUser.data && username === loginUser.data.username;

  const [selectedMenu, setSelectedMenu] = useState('');

  const [isProfileEditing, setIsProfileEditing] = useState(false);
  const [nickname, setNickname] = useState('');

  const { response: user, fetchData: getProfile } = useRequest(
    {},
    () => requestGetProfile(username),
    (data) => {
      setNickname(data.nickname);
    }
  );

  const { mutate: editProfile } = useMutation(
    () => {
      if (!isOwner) {
        setIsProfileEditing(false);

        return;
      }

      if (nickname.length > PROFILE.NICKNAME.MAX_LENGTH) {
        alert(ALERT_MESSAGE.OVER_PROFILE_NICKNAME_MAX_LENGTH);

        return;
      }

      return requestEditProfile({ username, nickname, imageUrl: user.imageUrl }, accessToken);
    },
    () => {
      setNickname(nickname);
      setIsProfileEditing(false);
    },
    () => {
      alert('회원 정보 수정에 실패했습니다.');
    }
  );

  const onSelectMenu = ({ key, path }) => () => {
    setSelectedMenu(key);
    history.push(path);
  };

  useEffect(() => {
    setNickname(nickname);
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
            <Nickname>{nickname}</Nickname>
          )}
          {isOwner && (
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
              onClick={onSelectMenu({ key: menuItem.key, path: menuItem.path })}
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
