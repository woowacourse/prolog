import React, { useCallback, useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, Card, Pagination } from '../../components';
import { requestDeleteScrap, requestGetMyScrap } from '../../service/requests';
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

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
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

  const onSetPage = (page) => {
    setPostQueryParams({ ...postQueryParams, page });
  };

  const onDeleteScrap = async (event, studylogId) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) return;

    try {
      const response = await requestDeleteScrap(username, accessToken, {
        studylogId,
      });

      if (!response.ok) {
        throw new Error(response.status);
      }

      getMyScrap();
    } catch (error) {
      console.error(error);
    }
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
                        onDeleteScrap(event, id);
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
