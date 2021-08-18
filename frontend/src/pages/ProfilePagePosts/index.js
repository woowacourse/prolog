import React, { useCallback, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { Button, BUTTON_SIZE, Tag, Calendar } from '../../components';
import { requestGetUserPosts, requestGetUserTags } from '../../service/requests';
import {
  Container,
  Content,
  CalendarWrapper,
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
  const [selectedTagId, setSelectedTagId] = useState(-1);
  const [filteringOption, setFilteringOption] = useState({});
  const [selectedDate, setSelectedDate] = useState('');

  const setDate = (year, month, day) =>
    setSelectedDate(`${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`);

  const { error: postError, deleteData: deletePost } = usePost({});
  const [tags] = useFetch([], () => requestGetUserTags(username));

  const goTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => (event) => {
    event.stopPropagation();

    history.push(`${PATH.POST}/${id}/edit`);
  };

  const getUserPosts = useCallback(async () => {
    try {
      const response = await requestGetUserPosts(username, filteringOption);

      if (!response.ok) {
        throw new Error(response.status);
      }
      const posts = await response.json();

      setPosts(posts.data);
    } catch (error) {
      console.error(error);
    }
  }, [username, filteringOption]);

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
  }, [username, getUserPosts]);

  useEffect(() => {
    if (selectedTagId === -1) {
      setFilteringOption({});
    } else {
      setFilteringOption({ tagId: selectedTagId });
    }
  }, [selectedTagId]);

  useEffect(() => {
    if (selectedDate === '') {
      setFilteringOption({});
    } else {
      setFilteringOption({ date: selectedDate });
    }
  }, [selectedDate]);

  /*
    selectedTagId가 바뀌면 그에따라 
      1. filteringOption을 {tagId: (ex)2 } 로 바꾸고 
      2. selectedDate 초기화
    selectedDate가 바뀌면 그에따라 
      1. filteringOption을 {date: asdas } 로 바꾸고
      2. selectedTagId 초기화
    
    이렇게 filteringOption을 공유하면 어떨까 합니다!
   */

  return (
    <Container>
      <div>
        <Tag
          id={-1}
          name="All"
          postCount={posts.length}
          selectedTagId={selectedTagId}
          onClick={() => setSelectedTagId(-1)}
        />
        {tags?.data?.map(({ id, name, postCount }) => (
          <Tag
            key={id}
            id={id}
            name={name}
            postCount={postCount}
            selectedTagId={selectedTagId}
            onClick={() => setSelectedTagId(id)}
          />
        ))}
      </div>
      <CalendarWrapper>
        <Calendar setSelectedDate={setDate} />
      </CalendarWrapper>
      {posts.length ? (
        posts.map((post) => {
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
                <Title isHovered={id === hoverdPostId}>{title}</Title>
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
