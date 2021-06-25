import React, { useState } from 'react';
import { css } from '@emotion/react';
import { useDispatch, useSelector } from 'react-redux';
import { Button, BUTTON_SIZE, FilterList, ProfileChip } from '../../components';
import useFetch from '../../hooks/useFetch';
import { requestGetPosts } from '../../service/requests';
import {
  Container,
  Profile,
  Image,
  Nickname,
  MenuList,
  MenuItem,
  MyPostList,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  PostItem,
  SectionTitle,
  ButtonList,
  Role,
} from './styles';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';

const CardHoverStyle = css`
  transition: transform 0.2s ease;
  cursor: pointer;

  &:hover {
    transform: scale(1.005);
  }
`;

const EditButtonStyle = css`
  border: 1px solid #e6e6e6;
  background-color: #fff;

  &:hover {
    background-color: #e8e8e8;
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid #e6e6e6;
  background-color: #f59898;

  &:hover {
    background-color: #f08484;
  }
`;

const MyPage = () => {
  const history = useHistory();
  const me = useSelector((state) => state.user.profile.data);

  const [hoverdPostId, setHoveredPostId] = useState(0);

  const [postList] = useFetch([], requestGetPosts);

  const goTargetPost = (id) => () => {
    history.push(`${PATH.POST}/${id}`);
  };

  return (
    <Container>
      <div>
        <Profile>
          <Image src={me?.imageUrl} alt="프로필 이미지" />
          <Role>{me?.role}</Role>
          <Nickname>{me?.nickname}</Nickname>
        </Profile>
        <MenuList>
          <MenuItem>
            <button>관리 홈</button>
          </MenuItem>
          <MenuItem>
            <button>글 관리</button>
          </MenuItem>
          <MenuItem>
            <button>내 정보 수정</button>
          </MenuItem>
        </MenuList>
      </div>
      <MyPostList>
        <SectionTitle>글 관리</SectionTitle>
        {postList?.map((post) => {
          const { id, mission, title, tags } = post;

          return (
            <PostItem
              key={id}
              size="SMALL"
              css={CardHoverStyle}
              onClick={goTargetPost(id)}
              onMouseOver={() => setHoveredPostId(id)}
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
              {hoverdPostId === id && (
                <ButtonList>
                  <Button size={BUTTON_SIZE.X_SMALL} css={EditButtonStyle} alt="수정 버튼">
                    수정
                  </Button>
                  <Button size={BUTTON_SIZE.X_SMALL} css={DeleteButtonStyle} alt="삭제 버튼">
                    삭제
                  </Button>
                </ButtonList>
              )}
            </PostItem>
          );
        })}
      </MyPostList>
    </Container>
  );
};

export default MyPage;
