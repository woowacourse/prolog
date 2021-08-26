import React, { useCallback, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, FilterList, Pagination } from '../../components';
import { requestGetFilters, requestGetPosts } from '../../service/requests';
import {
  ButtonList,
  Container,
  Content,
  DeleteButtonStyle,
  Description,
  EditButtonStyle,
  HeaderContainer,
  Mission,
  NoPost,
  PostItem,
  PostListContainer,
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

  const [hoveredPostId, setHoveredPostId] = useState(0);
  const [posts, setPosts] = useState([]);

  const [filters] = useFetch([], requestGetFilters);

  const { error: postError, deleteData: deletePost } = usePost({});

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => (event) => {
    event.stopPropagation();

    history.push(`${PATH.POST}/${id}/edit`);
  };

  const getUserPosts = useCallback(async () => {
    try {
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
  }, [getFullParams, history, postQueryParams, selectedFilterDetails, username]);

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

  useEffect(() => {
    getUserPosts();
  }, [getUserPosts]);

  return (
    <Container>
      <HeaderContainer>
        <FilterListWrapper>
          <FilterList
            filters={filters}
            selectedFilter={selectedFilter}
            setSelectedFilter={setSelectedFilter}
            selectedFilterDetails={selectedFilterDetails}
            setSelectedFilterDetails={onFilterChange}
            isVisibleResetFilter={!!selectedFilterDetails.length}
            onResetFilter={resetFilter}
          />
        </FilterListWrapper>
      </HeaderContainer>
      <PostListContainer>
        {posts?.data?.length ? (
          <>
            {posts?.data?.map((post) => {
              const { id, mission, title, tags } = post;

              return (
                <PostItem
                  key={id}
                  size="SMALL"
                  onClick={() => goTargetPost(id)}
                  onMouseEnter={() => setHoveredPostId(id)}
                  onMouseLeave={() => setHoveredPostId(0)}
                >
                  <Content>
                    <Description>
                      <Mission>{mission.name}</Mission>
                      <Title>{title}</Title>
                      <Tags>
                        {tags.map(({ id, name }) => (
                          <span key={id}>{`#${name} `}</span>
                        ))}
                      </Tags>
                    </Description>
                  </Content>
                  {hoveredPostId === id && myName === username && (
                    <ButtonList>
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
                  )}
                </PostItem>
              );
            })}
            <Pagination postsInfo={posts} onSetPage={onSetPage} />
          </>
        ) : (
          <NoPost>작성한 글이 없습니다 🥲</NoPost>
        )}
      </PostListContainer>
    </Container>
  );
};

export default ProfilePagePosts;
