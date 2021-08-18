import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, Tag } from '../../components';
import { requestGetUserPosts, requestGetUserTags } from '../../service/requests';
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
import useFetch from '../../hooks/useFetch';

const ProfilePagePosts = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const myName = useSelector((state) => state.user.profile.data?.username);
  const { username } = useParams();

  const [hoverdPostId, setHoveredPostId] = useState(0);
  const [posts, setPosts] = useState([]);

  const { error: postError, deleteData: deletePost } = usePost({});
  const [tags] = useFetch([], () => requestGetUserTags(username));

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => (event) => {
    event.stopPropagation();

    history.push(`${PATH.POST}/${id}/edit`);
  };

  const getUserPosts = async () => {
    try {
      const response = await requestGetUserPosts(username);

      if (!response.ok) {
        throw new Error(response.status);
      }
      const posts = await response.json();

      setPosts(posts.data);
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

  useEffect(() => {
    getUserPosts();
  }, [username]);

  return (
    <Container>
      <div>
        {tags?.data?.map(({ id, name, postCount }) => (
          <Tag key={id} tag={name} postCount={postCount} />
        ))}
      </div>
      <div>calendar을 넣어줘 디토~!</div>
      {posts.length ? (
        posts.map((post) => {
          console.log(post);
          const { id, mission, title, tags, content } = post;

          return (
            <PostItem
              key={id}
              size="SMALL"
              onClick={() => goTargetPost(id)}
              onMouseEnter={() => setHoveredPostId(id)}
              onMouseLeave={() => setHoveredPostId(0)}
            >
              <Description>
                <Mission>{mission.name}</Mission>
                <Title>{title}</Title>
                <Content>{content}</Content>
                <Tags>
                  {tags.map(({ id, name }) => (
                    <span key={id}>{`#${name} `}</span>
                  ))}
                </Tags>
              </Description>
              <ButtonList isVisible={hoverdPostId === id && myName === username}>
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
