import React, { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { SelectBox, Button, BUTTON_SIZE, EditPostCard } from '../../components';
import { useSelector } from 'react-redux';
import useFetch from '../../hooks/useFetch';
import { requestGetMissions, requestGetPost, requestGetTags } from '../../service/requests';
import { PATH } from '../../constants';
import usePost from '../../hooks/usePost';

// TODO: section 으로 바꾸기 -> aria-label 주기
const SelectBoxWrapper = styled.div`
  margin: 3rem 0;
`;

const Post = styled.div`
  margin-bottom: 4.8rem;
`;

const SubmitButtonStyle = css`
  width: 100%;
  background-color: #153147;
  color: #ffffff;
  font-weight: 500;
  margin: 0;
`;

const EditPostPage = () => {
  const history = useHistory();
  const user = useSelector((state) => state.user.profile.data?.nickname);
  const accessToken = useSelector((state) => state.user.accessToken.data);

  const { id: postId } = useParams();
  const [postResponse, postError, getAllPost, getPost, editPost] = usePost({});
  const [post, getPostError] = useFetch({}, () => requestGetPost(postId));
  const { id, author, mission } = post;

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);
  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  const cardRefs = useRef([]);

  const [selectedMission, setSelectedMission] = useState();

  useEffect(() => {
    setSelectedMission(mission?.name);
  }, [mission]);

  useEffect(() => {
    if (user !== author) {
      alert('접근 권한이 없습니다.');
      history.push(`${PATH.POST}/${postId}`);
    }
  }, [user, author]);

  const onEditPost = async (event) => {
    event.preventDefault();

    const { title, content, tags } = cardRefs.current;
    const data = {
      missionId: missions.find((mission) => mission.name === selectedMission).id,
      title: title.value,
      content: content.getInstance().getMarkdown(),
      tags:
        tags?.map((tag) => ({ name: tag.value })) || post.tags.map((tag) => ({ name: tag.name })),
    };

    await editPost(postId, data, accessToken);

    if (postError) {
      alert('글을 수정할 수 없습니다. 다시 시도해주세요');
      return;
    }

    history.push('/');
  };

  return (
    <form onSubmit={onEditPost}>
      <SelectBoxWrapper>
        <SelectBox
          options={missions?.map((mission) => mission.name)}
          selectedOption={selectedMission}
          setSelectedOption={setSelectedMission}
        />
      </SelectBoxWrapper>
      <Post key={id}>
        <EditPostCard ref={cardRefs} post={post} tagOptions={tagOptions} />
      </Post>
      <Button size={BUTTON_SIZE.SMALL} css={SubmitButtonStyle}>
        작성완료
      </Button>
    </form>
  );
};

export default EditPostPage;
