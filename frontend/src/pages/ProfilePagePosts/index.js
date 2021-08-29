import React, { useCallback, useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, FilterList, Pagination, Tag, Calendar, Card } from '../../components';
import {
  requestGetFilters,
  requestGetPosts,
  requestGetUserPosts,
  requestGetUserTags,
} from '../../service/requests';
import {
  ButtonList,
  Container,
  Content,
  DeleteButtonStyle,
  CardStyles,
  Description,
  EditButtonStyle,
  TagTitle,
  HeaderContainer,
  Mission,
  NoPost,
  PostItem,
  Tags,
  Title,
  FilterListWrapper,
} from './styles';
import { useSelector } from 'react-redux';
import usePost from '../../hooks/usePost';
import useFetch from '../../hooks/useFetch';
import useFilterWithParams from '../../hooks/useFilterWithParams';

const ProfilePagePosts = () => {
  const {
    postQueryParams,
    selectedFilter,
    setSelectedFilter,
    selectedFilterDetails,
    onSetPage,
    onFilterChange,
    resetFilter,
    getFullParams,
  } = useFilterWithParams();

  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const myName = useSelector((state) => state.user.profile.data?.username);
  const { username } = useParams();
  const { state } = useLocation();

  const [shouldInitialLoad, setShouldInitialLoad] = useState(!state);
  const [hoveredPostId, setHoveredPostId] = useState(0);
  const [posts, setPosts] = useState([]);
  const [selectedTagId, setSelectedTagId] = useState(0);
  const [selectedDay, setSelectedDay] = useState(state ? state.date.day : -1);
  const [filteringOption, setFilteringOption] = useState({});
  const [filters] = useFetch([], requestGetFilters);

  const { error: postError, deleteData: deletePost } = usePost({});
  const [tags] = useFetch([], () => requestGetUserTags(username));

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => (event) => {
    event.stopPropagation();

    history.push(`${PATH.POST}/${id}/edit`);
  };

  const getUserPosts = useCallback(async () => {
    try {
      // TODO : const response = await requestGetUserPosts(username, postQueryParams, filteringOption);
      const response = await requestGetPosts(
        [...selectedFilterDetails, { filterType: 'usernames', filterDetailId: username }],
        postQueryParams
      );

      if (!response.ok) {
        throw new Error(response.status);
      }

      const posts = await response.json();

      setPosts(posts);

      const params = getFullParams();

      history.push(`${PATH.ROOT}${username}/posts?${params}`);
    } catch (error) {
      console.error(error);
    }
  }, [getFullParams, history, postQueryParams, selectedFilterDetails, username, filteringOption]);

  const onDeletePost = async (event, id) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    await deletePost(id, accessToken);

    if (postError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_POST);
      return;
    }

    getUserPosts();
  };

  const setFilteringOptionWithTagId = (id) => setFilteringOption({ tagId: id });

  const setFilteringOptionWithDate = (year, month, day) =>
    setFilteringOption({
      date: `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`,
    });

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

  return (
    <Container>
      <HeaderContainer>
        <FilterListWrapper>
          <FilterList
            filters={{ levels: filters['levels'], missions: filters['missions'] }}
            selectedFilter={selectedFilter}
            setSelectedFilter={setSelectedFilter}
            selectedFilterDetails={selectedFilterDetails}
            setSelectedFilterDetails={onFilterChange}
            isVisibleResetFilter={!!selectedFilterDetails.length}
            onResetFilter={resetFilter}
          />
        </FilterListWrapper>
      </HeaderContainer>
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
      <Card title="캘린더">
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
              const { id, mission, title, tags, content } = post;

              return (
                <PostItem
                  key={id}
                  size="SMALL"
                  onClick={() => goTargetPost(id)}
                  onMouseEnter={() => setHoveredPostId(id)}
                  onMouseLeave={() => setHoveredPostId(0)}
                >
                  <Description>
                    <Mission>{mission.name}</Mission>
                    <Title isHovered={id === hoveredPostId}>{title}</Title>
                    <Content>{content}</Content>
                    <Tags>
                      {tags.map(({ id, name }) => (
                        <span key={id}>{`#${name} `}</span>
                      ))}
                    </Tags>
                  </Description>
                  <ButtonList isVisible={hoveredPostId === id && myName === username}>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={EditButtonStyle}
                      alt="수정 버튼"
                      onClick={goEditTargetPost(id)}
                    >
                      수정
                    </Button>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={DeleteButtonStyle}
                      alt="삭제 버튼"
                      onClick={(e) => {
                        onDeletePost(e, id);
                      }}
                    >
                      삭제
                    </Button>
                  </ButtonList>
                </PostItem>
              );
            })}
            <Pagination postsInfo={posts} onSetPage={onSetPage} />
          </>
        ) : (
          <NoPost>작성한 글이 없습니다 🥲</NoPost>
        )}
      </Card>
    </Container>
  );
};

export default ProfilePagePosts;
