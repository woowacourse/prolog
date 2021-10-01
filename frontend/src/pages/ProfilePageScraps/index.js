import React, { useCallback, useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, Card, Pagination } from '../../components';
import { requestGetMyScrap } from '../../service/requests';
import {
  ButtonList,
  CardStyles,
  Container,
  Content,
  DeleteButtonStyle,
  Description,
  Mission,
  NoPost,
  PostItem,
  Tags,
  Title,
  Heading,
} from './styles';
import { useSelector } from 'react-redux';
import usePost from '../../hooks/usePost';

const initialPostQueryParams = {
  page: 1,
  size: 10,
  direction: 'desc',
};

const ProfilePageScraps = () => {
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const { username } = useParams();
  const { state } = useLocation();

  const [shouldInitialLoad, setShouldInitialLoad] = useState(!state);
  const [hoveredPostId, setHoveredPostId] = useState(0);
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

  const getMyScrap = useCallback(async () => {
    try {
      const response = await requestGetMyScrap(username, accessToken, postQueryParams);
      const data = await response.json();

      setPosts(data);
    } catch (error) {
      console.error(error);
    }
  }, [postQueryParams, username]);

  const onDeletePost = async (event, id) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    await deletePost(id, accessToken);

    if (postError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_POST);
      return;
    }

    await getMyScrap();
  };

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  useEffect(() => {
    if (!shouldInitialLoad) {
      setShouldInitialLoad(true);

      return;
    }

    getMyScrap();
  }, [history.location.search, getMyScrap]);

  return (
    <Container>
      <Heading>스크랩</Heading>
      <Card css={CardStyles}>
        {posts?.data?.length ? (
          <>
            {posts?.data?.map((post) => {
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
                    <Title isHovered={id === hoveredPostId}>{title}</Title>
                    <Content>{content}</Content>
                    <Tags>
                      {tags.map(({ id, name }) => (
                        <span key={id}>{`#${name} `}</span>
                      ))}
                    </Tags>
                  </Description>
                  <ButtonList isVisible={hoveredPostId === id}>
                    <Button
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      css={DeleteButtonStyle}
                      alt="삭제 버튼"
                      onClick={(event) => {
                        onDeletePost(event, id);
                      }}
                    >
                      스크랩 취소
                    </Button>
                  </ButtonList>
                </PostItem>
              );
            })}
            <Pagination postsInfo={posts} onSetPage={onSetPage} />
          </>
        ) : (
          <NoPost>스크랩한 글이 없습니다 🥲</NoPost>
        )}
      </Card>
    </Container>
  );
};

export default ProfilePageScraps;
