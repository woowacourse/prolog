import { useState, useEffect, useContext } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useQuery } from 'react-query';
import axios from 'axios';

import { requestEditProfile, requestGetProfile } from '../../service/requests';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import { UserContext } from '../../contexts/UserProvider';

import { Button, BUTTON_SIZE } from '..';
import BadgeList from '../Badge/BadgeList';

import getMenuList from './getMenuList';

import { BASE_URL } from '../../configs/environment';
import { PROFILE } from '../../constants/input';
import { ALERT_MESSAGE } from '../../constants';

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

const ProfilePageSideBar = ({ menu }) => {
  const history = useHistory();
  const { username } = useParams();

  const { user: loginUser } = useContext(UserContext);
  const { accessToken, username: loginUsername } = loginUser;

  const isOwner = username === loginUsername;

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

  const { data: badgeList = [], isLoading } = useQuery([`${username}-badges`], async () => {
    const {
      data: { badges },
    } = await axios({
      method: 'get',
      url: `${BASE_URL}/members/${username}/badges`,
    });

    return badges;
  });

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
    {
      onSuccess: () => {
        // TODO: ?????? ?????? ????????? ???????????????
        setNickname(nickname);
        setIsProfileEditing(false);
      },
      onError: () => {
        alert('?????? ?????? ????????? ??????????????????.');
      },
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
        <Image src={user?.imageUrl} alt="????????? ?????????" /> <Role>{user?.role}</Role>
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
              alt={isProfileEditing ? '?????? ?????? ??????' : '?????? ??????'}
              onClick={() => {
                isProfileEditing ? editProfile() : setIsProfileEditing(true);
              }}
            >
              {isProfileEditing ? '??????' : '??????'}
            </Button>
          )}
        </NicknameWrapper>
      </Profile>
      {isLoading ? <></> : <BadgeList badgeList={badgeList} />}
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
