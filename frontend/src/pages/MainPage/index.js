import React, { useState, useEffect } from 'react';
import { Button, Card, FilterList, ProfileChip } from '../../components';
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

const MainPage = () => {
  const history = useHistory();
  const user = useSelector((state) => state.user.profile);
  const isLoggedIn = !!user.data;

  const [posts, setPosts] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState('');
  const [selectedFilterDetails, setSelectedFilterDetails] = useState([]);

  const [postList] = useFetch([], requestGetPosts);
  const [filters] = useFetch([], requestGetFilters);
  // if (error) {
  //   return <>글이 없습니다.</>;
  // }

  const goTargetPost = (id) => () => {
    history.push(`${PATH.POST}/${id}`);
  };

  const resetFilter = () => {
    setSelectedFilterDetails([]);
  };

  useEffect(() => {
    if (selectedFilterDetails === []) return;

    const getFilteredData = async () => {
      try {
        const response = await requestGetFilteredPosts(selectedFilterDetails);

        setPosts(await response.json());
      } catch (error) {
        console.error(error);
      }
    };

    getFilteredData();
  }, [selectedFilterDetails]);

  useEffect(() => {
    setPosts(postList);
  }, [postList]);

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
        {isLoggedIn && (
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
        {posts &&
          posts.data &&
          posts.data.map((post) => {
            const { id, author, mission, title, tags } = post;

            return (
              <Card key={id} size="SMALL" css={CardHoverStyle} onClick={goTargetPost(id)}>
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
                  <ProfileChip imageSrc={author.imageUrl} css={ProfileChipLocationStyle}>
                    {author.nickname}
                  </ProfileChip>
                </Content>
              </Card>
            );
          })}
      </PostListContainer>
    </>
  );
};

export default MainPage;
