import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router';
import { useHistory, useLocation } from 'react-router-dom';
import { ReactComponent as PostIcon } from '../../assets/images/post.svg';
import { ReactComponent as OverviewIcon } from '../../assets/images/overview.svg';
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
  Overview,
  SideBar,
  TagTitle,
  CardStyles,
  PostItem,
  Mission,
  Tags,
  NoPost,
  Title,
  Description,
  PostBottomContainer,
} from './styles';
import { PATH, PROFILE_PAGE_MENU } from '../../constants';
import { requestGetProfile, requestGetUserPosts, requestGetUserTags } from '../../service/requests';
import useNotFound from '../../hooks/useNotFound';
import { Calendar, Card, Pagination, Tag } from '../../components';
import useFetch from '../../hooks/useFetch';

const initialPostQueryParams = {
  page: 1,
  size: 5,
  direction: 'desc',
};

const ProfilePage = ({ children, menu }) => {
  const history = useHistory();
  const { username } = useParams();
  const { state } = useLocation();

  const [user, setUser] = useState({});
  const [selectedMenu, setSelectedMenu] = useState('');
  const [selectedTagId, setSelectedTagId] = useState(0);
  const [selectedDay, setSelectedDay] = useState(state ? state.date.day : -1);
  const [filteringOption, setFilteringOption] = useState({ tagId: 0 });
  const [posts, setPosts] = useState([]);
  const [shouldInitialLoad, setShouldInitialLoad] = useState(!state);
  const [hoveredPostId, setHoveredPostId] = useState(0);
  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);

  const { isNotFound, setNotFound, NotFound } = useNotFound();
  const [tags] = useFetch([], () => requestGetUserTags(username));

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

  const goProfilePagePostsWithDate = (year, month, day) => {
    history.push(`/${username}/posts`, { date: { year, month, day } });
    setSelectedMenu(PROFILE_PAGE_MENU.POSTS);
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

  const getUserPosts = useCallback(async () => {
    try {
      const response = await requestGetUserPosts(username, postQueryParams, filteringOption);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const posts = await response.json();
      console.log(posts);
      setPosts(posts);
    } catch (error) {
      console.error(error);
    }
  }, [postQueryParams, filteringOption, username]);

  const setFilteringOptionWithTagId = (id) => setFilteringOption({ tagId: id });

  const setFilteringOptionWithDate = (year, month, day) =>
    setFilteringOption({
      date: `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`,
    });

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  useEffect(() => {
    getProfile();
    setSelectedMenu(menu);
  }, [username]);

  useEffect(() => {
    if (!shouldInitialLoad) {
      setShouldInitialLoad(true);

      return;
    }

    getUserPosts();
  }, [username, getUserPosts, shouldInitialLoad]);

  useEffect(() => {
    if (!state) return;

    setFilteringOptionWithDate(state.date.year, state.date.month, state.date.day);
  }, [state]);

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
          <Overview>
            <div>
              <TagTitle>태그</TagTitle>
              {tags?.data?.map(({ id, name, count }) => (
                <Tag
                  key={id}
                  id={id}
                  name={name}
                  postCount={count}
                  selectedTagId={selectedTagId}
                  onClick={() => {
                    setSelectedTagId(id);
                    setSelectedDay(-1);
                    setFilteringOptionWithTagId(id);
                  }}
                />
              ))}
            </div>
            <Card title="캘린더" css={CardStyles}>
              <Calendar
                newDate={state?.date}
                onClick={(year, month, day) => {
                  setSelectedTagId(null);
                  setFilteringOptionWithDate(year, month, day);
                }}
                selectedDay={selectedDay}
                setSelectedDay={setSelectedDay}
              />
            </Card>
            <Card title="학습로그" css={CardStyles}>
              {posts?.data?.length ? (
                <>
                  {posts?.data?.map((post) => {
                    const { id, mission, title, tags, createdAt } = post;

                    return (
                      <PostItem
                        key={id}
                        size="SMALL"
                        onClick={() => goTargetPost(id)}
                        onMouseEnter={() => setHoveredPostId(id)}
                        onMouseLeave={() => setHoveredPostId(0)}
                      >
                        <Description>
                          <Title isHovered={id === hoveredPostId}>{title}</Title>
                          <PostBottomContainer>
                            <Mission>{mission.name}</Mission>
                            {!!tags.length && (
                              <Tags>
                                {tags.map(({ id, name }) => (
                                  <span key={id}>{`#${name} `}</span>
                                ))}
                              </Tags>
                            )}

                            <div>{new Date(createdAt).toLocaleString('ko-KR')}</div>
                          </PostBottomContainer>
                        </Description>
                      </PostItem>
                    );
                  })}
                  <Pagination postsInfo={posts} onSetPage={onSetPage} />
                </>
              ) : (
                <NoPost>작성한 글이 없습니다 🥲</NoPost>
              )}
            </Card>
          </Overview>
        )}
      </Content>
    </Container>
  );
};

export default ProfilePage;
