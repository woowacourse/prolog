import React, { useEffect, useState } from 'react';
import { css } from '@emotion/react';
import { useHistory } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE } from '../../components';
import { requestGetMyPosts } from '../../service/requests';
import {
  Container,
  Content,
  Description,
  Mission,
  Title,
  Tags,
  PostItem,
  ButtonList,
  NoPost,
} from './styles';
import { useSelector } from 'react-redux';
import usePost from '../../hooks/usePost';

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

const MyPagePosts = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const nickname = useSelector((state) => state.user.profile.data?.nickname);

  const [hoverdPostId, setHoveredPostId] = useState(0);
  const [posts, setPosts] = useState([]);

  const { error: postError, deleteData: deletePost } = usePost({});

  const goTargetPost = (id) => (event) => {
    if (event?.target !== event?.currentTarget) return;

    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}/edit`);
  };

  const getMyPosts = async () => {
    try {
      const response = await requestGetMyPosts(nickname, accessToken);

      if (!response.ok) {
        throw new Error(response.status);
      }

      setPosts(await response.json());
    } catch (error) {
      console.error(error);
    }
  };

  const onDeletePost = async (id) => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    await deletePost(id, accessToken);

    if (postError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_POST);
      return;
    }

    getMyPosts();
  };

  useEffect(() => {
    getMyPosts();
  }, [nickname]);

  return (
    <Container>
      {posts.length ? (
        posts.map((post) => {
          const { id, mission, title, tags } = post;

          return (
            <PostItem
              key={id}
              size="SMALL"
              onClick={goTargetPost(id)}
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
              {hoverdPostId === id && (
                <ButtonList>
                  <Button
                    size={BUTTON_SIZE.X_SMALL}
                    type="button"
                    css={EditButtonStyle}
                    alt="수정 버튼"
                    onClick={() => goEditTargetPost(id)}
                  >
                    수정
                  </Button>
                  <Button
                    size={BUTTON_SIZE.X_SMALL}
                    type="button"
                    css={DeleteButtonStyle}
                    alt="삭제 버튼"
                    onClick={() => onDeletePost(id)}
                  >
                    삭제
                  </Button>
                </ButtonList>
              )}
            </PostItem>
          );
        })
      ) : (
        <NoPost>작성한 글이 없습니다 🥲</NoPost>
      )}
    </Container>
  );
};

export default MyPagePosts;
