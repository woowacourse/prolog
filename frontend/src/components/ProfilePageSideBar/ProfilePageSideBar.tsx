import { useState, useContext } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useQuery } from 'react-query';
import { UserContext } from '../../contexts/UserProvider';
import BadgeList from '../Badge/BadgeList';
import getMenuList from './getMenuList';
import { Button, BUTTON_SIZE } from '..';
import { BASE_URL } from '../../configs/environment';
import { useGetProfileQuery, usePutProfileMutation } from '../../hooks/queries/profile';
import { createAxiosInstance } from '../../utils/axiosInstance';
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

interface ProfilePageSideBarProps {
  menu: string;
}

const ProfilePageSideBar = ({ menu }: ProfilePageSideBarProps) => {
  const history = useHistory();
  const { username } = useParams<{ username: string }>();

  const { user: loginUser } = useContext(UserContext);
  const { accessToken, username: loginUsername } = loginUser;

  const isOwner = username === loginUsername;

  const [selectedMenu, setSelectedMenu] = useState(menu);

  const [isProfileEditing, setIsProfileEditing] = useState(false);
  const [nickname, setNickname] = useState('');

  const instance = createAxiosInstance();

  const { data: user } = useGetProfileQuery(
    { username },
    {
      onSuccess: (data) => {
        setNickname(data.nickname);
      },
    }
  );

  const { data: badgeList = [], isLoading } = useQuery([`${username}-badges`], async () => {
    const {
      data: { badges },
    } = await instance.get(`${BASE_URL}/members/${username}/badges`);

    return badges;
  });

  const { mutate: editProfile } = usePutProfileMutation(
    {
      user,
      nickname,
      accessToken,
    },
    {
      onSuccess: () => {
        setIsProfileEditing(false);
      },
    }
  );

  const onSelectMenu = ({ key, path }) => () => {
    setSelectedMenu(key);
    history.push(path);
  };

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
      {isLoading ? <></> : <BadgeList badgeList={badgeList} />}
      <MenuList>
        {getMenuList({ username, isOwner }).map((menuItem) => (
          <MenuItem key={menuItem.key} isSelectedMenu={selectedMenu === menuItem?.key}>
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
