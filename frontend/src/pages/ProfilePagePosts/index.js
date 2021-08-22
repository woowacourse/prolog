import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, Pagination } from '../../components';
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

const initialPostQueryParams = {
  page: 1,
  size: 10,
  direction: 'desc',
};

const ProfilePagePosts = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const myName = useSelector((state) => state.user.profile.data?.username);
  const { username } = useParams();

  const [hoverdPostId, setHoveredPostId] = useState(0);
  const [posts, setPosts] = useState([]);
  const [postQueryParams, setPostQueryParams] = useState(initialPostQueryParams);

  const { error: postError, deleteData: deletePost } = usePost({});

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => (event) => {
    event.stopPropagation();

    history.push(`${PATH.POST}/${id}/edit`);
  };

  const getUserPosts = async () => {
    try {
      const response = await requestGetUserPosts(username, postQueryParams);

      if (!response.ok) {
        throw new Error(response.status);
      }

      const posts = await response.json();

      setPosts(posts);
    } catch (error) {
      console.error(error);
    }
  };

  const onDeletePost = async (event, id) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    await deletePost(id, accessToken);

    if (postError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_POST);
      return;
    }

    getUserPosts();
  };

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  useEffect(() => {
    getUserPosts();
  }, [username, postQueryParams]);

  return (
    <Container>
      {posts?.data?.length ? (
        <>
          {posts?.data?.map((post) => {
            const { id, mission, title, tags } = post;

            return (
              <PostItem
                key={id}
                size="SMALL"
                onClick={() => goTargetPost(id)}
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
                {hoverdPostId === id && myName === username && (
                  <ButtonList>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={EditButtonStyle}
                      alt="수정 버튼"
                      onClick={goEditTargetPost(id)}
                    >
                      수정
                    </Button>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={DeleteButtonStyle}
                      alt="삭제 버튼"
                      onClick={(e) => {
                        onDeletePost(e, id);
                      }}
                    >
                      삭제
                    </Button>
                  </ButtonList>
                )}
              </PostItem>
            );
          })}
          <Pagination postsInfo={posts} onSetPage={onSetPage} />
        </>
      ) : (
        <NoPost>작성한 글이 없습니다 🥲</NoPost>
      )}
    </Container>
  );
};

export default ProfilePagePosts;
