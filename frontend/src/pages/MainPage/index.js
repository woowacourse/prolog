import React, { useState, useEffect } from 'react';
import { Button, Card, FilterList, ProfileChip, Pagination } from '../../components';
import { useHistory } from 'react-router';
import { PATH } from '../../constants';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import useFetch from '../../hooks/useFetch';
import {
  requestGetPosts,
  requestGetFilters,
  requestGetFilteredPosts,
} from '../../service/requests';
import { useSelector } from 'react-redux';
import {
  HeaderContainer,
  FilterListWrapper,
  PostListContainer,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  ProfileChipLocationStyle,
  CardHoverStyle,
} from './styles';
import { ERROR_MESSAGE } from '../../constants/message';

const initialPostQueryParams = {
  page: 1,
  size: 10,
  direction: 'desc',
};

const MainPage = () => {
  const history = useHistory();
  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;

  const [posts, setPosts] = useState([]);
  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);
  const [selectedFilter, setSelectedFilter] = useState('');
  const [selectedFilterDetails, setSelectedFilterDetails] = useState([]);

  const [postsInfo, setPostsInfo] = useState([]);
  const [filters] = useFetch([], requestGetFilters);

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const resetFilter = () => {
    setSelectedFilterDetails([]);
  };

  const goProfilePage = (username) => (event) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  const onFilterChange = (value) => {
    setPostQueryParams({ ...postQueryParams, page: 1 });
    setSelectedFilterDetails(value);
  };

  const goNewPost = () => {
    const accessToken = localStorage.getItem('accessToken');

    if (!accessToken) {
      alert(ERROR_MESSAGE.LOGIN_DEFAULT);
      window.location.reload();
      return;
    }

    history.push(PATH.NEW_POST);
  };

  useEffect(() => {
    if (selectedFilterDetails === []) return;

    const getData = async () => {
      try {
        const response = await requestGetPosts(selectedFilterDetails, postQueryParams);
        const data = await response.json();

        setPostsInfo(data);
        setPosts(data);
      } catch (error) {
        console.error(error);
      }
    };

    getData();
  }, [selectedFilterDetails, postQueryParams]);

  return (
    <>
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
        {isLoggedIn && (
          <Button
            type="button"
            size="SMALL"
            icon={PencilIcon}
            alt="글쓰기 아이콘"
            onClick={goNewPost}
          >
            글쓰기
          </Button>
        )}
      </HeaderContainer>
      <PostListContainer>
        {posts?.data?.length === 0 && '작성된 글이 없습니다.'}
        {posts &&
          posts.data &&
          posts.data.map((post) => {
            const { id, author, mission, title, tags } = post;

            return (
              <Card key={id} size="SMALL" css={CardHoverStyle} onClick={() => goTargetPost(id)}>
                <Content>
                  <Description>
                    <Mission>{mission.name}</Mission>
                    <Title>{title}</Title>
                    <Tags>
                      {tags?.map(({ id, name }) => (
                        <span key={id}>{`#${name} `}</span>
                      ))}
                    </Tags>
                  </Description>
                  <ProfileChip
                    imageSrc={author.imageUrl}
                    css={ProfileChipLocationStyle}
                    onClick={goProfilePage(author.username)}
                  >
                    {author.nickname}
                  </ProfileChip>
                </Content>
              </Card>
            );
          })}
      </PostListContainer>
      <Pagination postsInfo={postsInfo} onSetPage={onSetPage}></Pagination>
    </>
  );
};

export default MainPage;
