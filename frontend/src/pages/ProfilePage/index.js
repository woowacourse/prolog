import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router';
import { useHistory, useLocation } from 'react-router-dom';
import {
  Container,
  Content,
  Overview,
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
import { PATH } from '../../constants';
import { requestGetUserPosts, requestGetUserTags } from '../../service/requests';
import useNotFound from '../../hooks/useNotFound';
import { Calendar, Card, Pagination, ProfilePageSideBar, Tag } from '../../components';
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

  const [selectedTagId, setSelectedTagId] = useState(0);
  const [selectedDay, setSelectedDay] = useState(state ? state.date.day : -1);
  const [filteringOption, setFilteringOption] = useState({ tagId: 0 });
  const [posts, setPosts] = useState([]);
  const [shouldInitialLoad, setShouldInitialLoad] = useState(!state);
  const [hoveredPostId, setHoveredPostId] = useState(0);
  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);

  const { isNotFound, setNotFound, NotFound } = useNotFound();
  const [tags] = useFetch([], () => requestGetUserTags(username));

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
      <ProfilePageSideBar menu={menu} />
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
