import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE } from '../../components';
import { requestGetUserPosts } from '../../service/requests';
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
  EditButtonStyle,
  DeleteButtonStyle,
} from './styles';
import { useSelector } from 'react-redux';
import usePost from '../../hooks/usePost';

const ProfilePagePosts = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const { username } = useParams();

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

  const getUserPosts = async () => {
    try {
      const response = await requestGetUserPosts(username, accessToken);
      console.log(response);
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

    getUserPosts();
  };

  useEffect(() => {
    getUserPosts();
  }, [username]);

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

export default ProfilePagePosts;
