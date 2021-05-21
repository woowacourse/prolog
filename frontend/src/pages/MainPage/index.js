import React from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { Button, Card, ProfileChip } from '../../components';
import { useHistory } from 'react-router';
import { PATH } from '../../constants';
import PencilIcon from '../../assets/images/pencil_icon.svg';
import useGetFetch from '../../hooks/useGetFetch';

const HeaderContainer = styled.div`
  height: 6.4rem;
  display: flex;
  margin-bottom: 3.7rem;
  justify-content: space-between;
`;

const FilterList = styled.div`
  background-color: #f4f4f4;
  border: 1px solid #707070;
  border-radius: 2rem;
  padding: 1rem 3rem;

  & button {
    height: 100%;
    margin-right: 2rem;
    font-size: 2rem;
    text-align: center;
  }
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

const Category = styled.div`
  font-size: 2rem;
  color: #383838;
`;

const Title = styled.h3`
  font-size: 3.6rem;
  color: #383838;
  font-weight: bold;
`;

const Tags = styled.div`
  font-size: 1.4rem;
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
    transform: scale(1.015);
  }
`;

const MainPage = () => {
  const history = useHistory();
  const { response: postList, error } = useGetFetch([], 'getAllData');

  if (error) {
    return <>글이 없습니다.</>;
  }

  const goTargetPost = (id) => () => {
    history.push(`${PATH.POST}/${id}`);
  };

  return (
    <>
      <HeaderContainer>
        <FilterList>
          <button>필터 1</button>
          <button>필터 2</button>
          <button>필터 3</button>
        </FilterList>
        <Button
          type="button"
          size="MEDIUM"
          icon={PencilIcon}
          alt="글쓰기 아이콘"
          onClick={() => history.push(PATH.NEW_POST)}
        >
          글쓰기
        </Button>
      </HeaderContainer>
      <PostListContainer>
        {postList.map((post) => {
          const { id, author, category, title, tags } = post;

          return (
            <Card key={id} size="SMALL" css={CardHoverStyle} onClick={goTargetPost(id)}>
              <Content>
                <Description>
                  <Category>{category.categoryName}</Category>
                  <Title>{title}</Title>
                  <Tags>
                    {tags.map((tag) => (
                      <span key={tag}>{`#${tag} `}</span>
                    ))}
                  </Tags>
                </Description>
                <ProfileChip imageSrc={author.imageUrl} css={ProfileChipLocationStyle}>
                  {author.nickName}
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