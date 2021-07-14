import React, { useState, useEffect } from 'react';
import { Button, Card, FilterList, ProfileChip, Pagination } from '../../components';
import { useHistory } from 'react-router';
import { PATH } from '../../constants';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import useFetch from '../../hooks/useFetch';
import { requestGetFilters, requestGetPosts } from '../../service/requests';
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

const initialPostQueryParams = {
  page: 1,
  size: 10,
  direction: 'desc',
};

const MainPage = () => {
  const history = useHistory();
  const isUserLoggedIn = useSelector((state) => state.user.accessToken.data);

  const [posts, setPosts] = useState([]);
  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);
  const [selectedFilter, setSelectedFilter] = useState('');
  const [selectedFilterDetails, setSelectedFilterDetails] = useState([]);

  const [postsInfo, setPostsInfo] = useState([]);
  const [filters] = useFetch([], requestGetFilters);

  const goTargetPost = (id) => () => {
    history.push(`${PATH.POST}/${id}`);
  };

  const resetFilter = () => {
    setSelectedFilterDetails([]);
  };

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
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
            setSelectedFilterDetails={setSelectedFilterDetails}
            isVisibleResetFilter={!!selectedFilterDetails.length}
            onResetFilter={resetFilter}
          />
        </FilterListWrapper>
        {isUserLoggedIn && (
          <Button
            type="button"
            size="SMALL"
            icon={PencilIcon}
            alt="글쓰기 아이콘"
            onClick={() => history.push(PATH.NEW_POST)}
          >
            글쓰기
          </Button>
        )}
      </HeaderContainer>
      <PostListContainer>
        {posts?.data?.map((post) => {
          const { id, author, mission, title, tags } = post;

          return (
            <Card key={id} size="SMALL" css={CardHoverStyle} onClick={goTargetPost(id)}>
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
                <ProfileChip imageSrc={author.imageUrl} css={ProfileChipLocationStyle}>
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
