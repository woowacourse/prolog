import React, { useEffect, useState } from 'react';
import { Button, Card, FilterList, Pagination, ProfileChip } from '../../components';
import { useHistory } from 'react-router';
import queryString from 'query-string';
import { PATH } from '../../constants';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import useFetch from '../../hooks/useFetch';
import { requestGetFilters, requestGetPosts } from '../../service/requests';
import { useSelector } from 'react-redux';
import {
  CardHoverStyle,
  Content,
  Description,
  FilterListWrapper,
  HeaderContainer,
  Mission,
  PostListContainer,
  ProfileChipLocationStyle,
  Tags,
  Title,
} from './styles';
import { ERROR_MESSAGE } from '../../constants/message';

const MainPage = (location) => {
  const query = queryString.parse(location.location.search);

  const pageParams = {
    page: query.page ? query.page : 1,
    size: query.size ? query.size : 10,
    direction: query.direction ? query.direction : 'desc',
  };

  const makeFilters = (filters, filterType) => {
    if (!filters) {
      return [];
    }
    if (filters.length > 1) {
      return filters.map((id) => ({ filterType: filterType, filterDetailId: Number(id) }));
    }
    return [{ filterType: filterType, filterDetailId: Number(filters) }];
  };

  const levelFilter = makeFilters(query.levels, 'levels');
  const missionFilter = makeFilters(query.missions, 'missions');
  const tagFilter = makeFilters(query.tags, 'tags');

  const filterParams = [...levelFilter, ...missionFilter, ...tagFilter];

  const history = useHistory();
  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;

  const [posts, setPosts] = useState([]);
  const [postQueryParams, setPostQueryParams] = useState(pageParams);
  const [selectedFilter, setSelectedFilter] = useState('');
  const [selectedFilterDetails, setSelectedFilterDetails] = useState(filterParams);

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

        setPosts(data);

        const pageParams = queryString.stringify(postQueryParams);
        const filterParams = selectedFilterDetails
          .map((filter) => {
            return filter.filterType + '=' + filter.filterDetailId;
          })
          .join('&');
        const params = pageParams + (filterParams ? '&' + filterParams : '');

        history.push(PATH.ROOT + '?' + params);
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
      <Pagination postsInfo={posts} onSetPage={onSetPage}></Pagination>
    </>
  );
};

export default MainPage;
