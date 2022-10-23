import { useState, useContext } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useQuery } from 'react-query';
import axios from 'axios';

import { UserContext } from '../../contexts/UserProvider';

import { Button, BUTTON_SIZE } from '..';
import BadgeList from '../Badge/BadgeList';

import getMenuList from './getMenuList';

import { BASE_URL } from '../../configs/environment';

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
  RoleContainer,
  PromoteRoleButton,
} from './ProfilePageSideBar.styles';
import {
  useGetProfileQuery,
  usePostRolePromotion,
  usePutProfileMutation,
} from '../../hooks/queries/profile';

const ProfilePageSideBar = ({ menu }) => {
  const history = useHistory();
  const { username } = useParams() as { username: string };

  const { user: loginUser } = useContext(UserContext);
  const { accessToken, username: loginUsername } = loginUser;

  const isOwner = username === loginUsername;

  const [selectedMenu, setSelectedMenu] = useState(menu);

  const [isProfileEditing, setIsProfileEditing] = useState(false);
  const [nickname, setNickname] = useState('');

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
    } = await axios({
      method: 'get',
      url: `${BASE_URL}/members/${username}/badges`,
    });

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

  const { mutate: promoteRole } = usePostRolePromotion(
    { accessToken },
    {
      onSuccess: () => {
        alert('등업 신청이 완료되었습니다🎉 등업은 최대 2~3주 소요될 수 있습니다.');
      },
    }
  );

  const onSelectMenu = ({ key, path }) => () => {
    setSelectedMenu(key);
    history.push(path);
  };

  const onPromoteRoleButton = () => {
    promoteRole();
  };

  return (
    <Container>
      <Profile>
        <Image src={user?.imageUrl} alt="프로필 이미지" />
        <RoleContainer>
          <Role>{user?.role}</Role>
          {isOwner ? (
            <PromoteRoleButton onClick={onPromoteRoleButton}>등업 신청</PromoteRoleButton>
          ) : (
            <div></div>
          )}
        </RoleContainer>
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
