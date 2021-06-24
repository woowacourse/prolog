import React, { useState, useEffect } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
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

const HeaderContainer = styled.div`
  height: 4.8rem;
  display: flex;
  margin-bottom: 3.7rem;
  justify-content: space-between;
`;

const FilterListWrapper = styled.div`
  width: 100%;
  height: inherit;
  margin-right: 2rem;
  flex: 1;
`;

const PostListContainer = styled.div`
  display: grid;
  grid-row-gap: 2rem;
`;

const Content = styled.div`
  display: flex;
  height: 100%;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const Mission = styled.div`
  font-size: 1.6rem;
  color: #383838;
`;

const Title = styled.h3`
  font-size: 2.8rem;
  color: #383838;
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.2rem;
  color: #848484;
  margin-top: auto;
`;

const ProfileChipLocationStyle = css`
  margin-left: auto;
`;

const CardHoverStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;

  &:hover {
    transform: scale(1.005);
  }
`;

const MainPage = () => {
  const history = useHistory();
  const isUserLoggedIn = useSelector((state) => state.user.accessToken.data);

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
        {posts?.map((post) => {
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
