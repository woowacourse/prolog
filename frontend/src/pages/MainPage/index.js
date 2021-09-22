import React, { useEffect, useState } from 'react';
import { Button, Card, FilterList, Pagination, ProfileChip } from '../../components';
import { useHistory } from 'react-router';
import { PATH } from '../../constants';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import useFetch from '../../hooks/useFetch';
import { requestGetFilters, requestGetPosts } from '../../service/requests';
import { useSelector } from 'react-redux';
import {
  CardStyle,
  Content,
  Description,
  FilterListWrapper,
  HeaderContainer,
  Mission,
  PostListContainer,
  ProfileChipLocationStyle,
  SelectedFilterList,
  Tags,
  Title,
} from './styles';
import { ERROR_MESSAGE } from '../../constants/message';
import Chip from '../../components/Chip/Chip';
import FlexBox from '../../components/@shared/FlexBox/FlexBox';
import useFilterWithParams from '../../hooks/useFilterWithParams';

const MainPage = () => {
  const {
    postQueryParams,
    selectedFilter,
    setSelectedFilter,
    selectedFilterDetails,
    setSelectedFilterDetails,
    onSetPage,
    onUnsetFilter,
    onFilterChange,
    resetFilter,
    getFullParams,
  } = useFilterWithParams();

  const history = useHistory();
  const search = new URLSearchParams(history.location.search).get('keyword');

  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;

  const [posts, setPosts] = useState([]);

  const [filters] = useFetch([], requestGetFilters);

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goProfilePage = (username) => (event) => {
    event.stopPropagation();

    history.push(`/${username}`);
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

  const onDeleteSearchKeyword = () => {
    const params = new URLSearchParams(history.location.search);
    params.delete('keyword');

    history.push(`${PATH.ROOT}?${params.toString()}`);
  };

  useEffect(() => {
    const params = getFullParams();

    if (!search && !params) {
      history.push(`${PATH.ROOT}`);
    } else if (params && !search) {
      history.push(`${PATH.ROOT}?${params ? params : ''}`);
    } else if (search && !params) {
      history.push(`${PATH.ROOT}?${search ? 'keyword=' + search : ''}`);
    } else {
      history.push(`${PATH.ROOT}?${search ? 'keyword=' + search : ''}&${params ?? ''}`);
    }
  }, [getFullParams, postQueryParams, selectedFilterDetails]);

  useEffect(() => {
    const getData = async () => {
      const query = new URLSearchParams(history.location.search);

      try {
        const response = await requestGetPosts({ type: 'searchParams', data: query });
        const data = await response.json();

        setPosts(data);
      } catch (error) {
        console.error(error);
      }
    };

    getData();
  }, [history.location.search]);

  useEffect(() => {
    if (filters.length === 0) {
      return;
    }

    const selectedFilterDetailsWithName = selectedFilterDetails.map(
      ({ filterType, filterDetailId }) => {
        const name = filters[filterType].find(({ id }) => id === filterDetailId)?.name;
        return { filterType, filterDetailId, name };
      }
    );

    setSelectedFilterDetails(selectedFilterDetailsWithName);
  }, [filters]);

  return (
    <>
      <HeaderContainer>
        <FlexBox>
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
        </FlexBox>

        <SelectedFilterList>
          <ul>
            {!!search && (
              <li>
                <Chip onDelete={onDeleteSearchKeyword}>{`검색어 : ${search}`}</Chip>
              </li>
            )}
            {selectedFilterDetails.map(({ filterType, filterDetailId, name }) => (
              <li key={filterType + filterDetailId + name}>
                <Chip
                  onDelete={() => onUnsetFilter({ filterType, filterDetailId })}
                >{`${filterType}: ${name}`}</Chip>
              </li>
            ))}
          </ul>
        </SelectedFilterList>
      </HeaderContainer>

      <PostListContainer>
        {posts?.data?.length === 0 && '작성된 글이 없습니다.'}
        {posts &&
          posts.data &&
          posts.data.map((post) => {
            const { id, author, mission, title, tags } = post;

            return (
              <Card key={id} size="SMALL" css={CardStyle} onClick={() => goTargetPost(id)}>
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
