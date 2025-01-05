/** @jsxImportSource @emotion/react */

import { useState, useContext } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useQuery } from 'react-query';
import { UserContext } from '../../contexts/UserProvider';
import getMenuList from './getMenuList';
import { Button, BUTTON_SIZE } from '..';
import { BASE_URL } from '../../configs/environment';
import { useGetProfileQuery, usePutProfileMutation } from '../../hooks/queries/profile';
import { createAxiosInstance } from '../../utils/axiosInstance';
import {
  Profile,
  Image,
  Nickname,
  RssFeedUrl,
  MenuList,
  MenuItem,
  MenuButton,
  Role,
  RoleButton,
  RoleLabel,
  RssLinkLabel,
  Container,
  EditButtonStyle,
  NicknameWrapper,
  NicknameInput,
  RssFeedWrapper,
  RssFeedInput,
  UpdateButton,
} from './ProfilePageSideBar.styles';
import { css } from '@emotion/react';
import { FlexStyle, JustifyContentEndStyle } from '../../styles/flex.styles';

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
  const [isRssFeedEditing, setIsRssFeedEditing] = useState(false);
  const [nickname, setNickname] = useState('');
  const [rssFeedUrl, setRssFeedUrl] = useState('');

  const instance = createAxiosInstance();

  const { data: user } = useGetProfileQuery(
    { username },
    {
      onSuccess: (data) => {
        setNickname(data.nickname);
        setRssFeedUrl(data.rssFeedUrl);
      },
    }
  );

  const { data: badgeList = [], isLoading } = useQuery([`${username}-badges`], async () => {
    const {
      data: { badges },
    } = await instance.get(`${BASE_URL}/members/${username}/badges`);

    return badges;
  });

  const { mutate: editNickname } = usePutProfileMutation(
    {
      user,
      nickname,
      rssFeedUrl,
      accessToken,
    },
    {
      onSuccess: () => {
        setIsProfileEditing(false);
      },
    }
  );

  const { mutate: editRssUrl } = usePutProfileMutation(
    {
      user,
      nickname,
      rssFeedUrl,
      accessToken,
    },
    {
      onSuccess: () => {
        setIsRssFeedEditing(false);
      },
    }
  );

  const onSelectMenu = ({ key, path }) => () => {
    setSelectedMenu(key);
    history.push(path);
  };

  const [showAll, setShowAll] = useState(false); // 상태 관리: 전체 표시 여부

  const toggleShowAll = () => setShowAll((prev) => !prev); // 상태 토글 함수

  const displayedGroups = showAll
    ? user?.organizationGroups // 전체 항목 표시
    : user?.organizationGroups?.slice(0, 2); // 첫 2개만 표시

  return (
    <Container>
      <Profile>
        <Image src={user?.imageUrl} alt="프로필 이미지" />
        <div
          css={[
            css`
              padding: 2rem;
            `,
          ]}
        >
          <RoleLabel>소속</RoleLabel>
          <Role>
            {displayedGroups && displayedGroups.length === 0 && <div>소속된 그룹이 없습니다.</div>}
            {displayedGroups?.map((group, index) => (
              <div key={index}>{group}</div>
            ))}

            {user?.organizationGroups?.length > 2 && (
              <RoleButton onClick={toggleShowAll}>{showAll ? '가리기' : '더보기'}</RoleButton>
            )}
          </Role>
          <RoleLabel>닉네임</RoleLabel>
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
              <UpdateButton
                type="button"
                css={EditButtonStyle}
                onClick={() => {
                  isProfileEditing ? editNickname() : setIsProfileEditing(true);
                }}
              >
                {isProfileEditing ? '완료' : '수정'}
              </UpdateButton>
            )}
          </NicknameWrapper>
          <RoleLabel>RSS Link</RoleLabel>
          <RssFeedWrapper>
            {isRssFeedEditing ? (
              <NicknameInput
                autoFocus
                value={rssFeedUrl}
                onChange={({ target }) => setRssFeedUrl(target.value)}
              />
            ) : (
              <Nickname>{rssFeedUrl}</Nickname>
            )}
            {isOwner && (
              <UpdateButton
                css={EditButtonStyle}
                onClick={() => {
                  isRssFeedEditing ? editRssUrl() : setIsRssFeedEditing(true);
                }}
              >
                {isRssFeedEditing ? '완료' : '수정'}
              </UpdateButton>
            )}
          </RssFeedWrapper>
        </div>
      </Profile>
      {/*{isLoading ? <></> : <BadgeList badgeList={badgeList} />}*/}
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
