import React, { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { SelectBox, Button, BUTTON_SIZE, EditPostCard } from '../../components';
import { useDispatch, useSelector } from 'react-redux';
import { createPost } from '../../redux/actions/postAction';
import useFetch from '../../hooks/useFetch';
import { requestGetMissions, requestGetPost, requestGetTags } from '../../service/requests';
import { PATH } from '../../constants';

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
  const dispatch = useDispatch();
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const user = useSelector((state) => state.user.profile.data?.nickname);
  const { error } = useSelector((state) => state.post.posts);

  const { id: postId } = useParams();
  const [post, getPostError] = useFetch({}, () => requestGetPost(postId));
  const { id, author, mission } = post;

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);
  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  const cardRefs = useRef([]);

  const [selectedMission, setSelectedMission] = useState('');

  useEffect(() => {
    setSelectedMission(mission?.name);
  }, [mission]);

  useEffect(() => {
    if (user !== author) {
      alert('접근 권한이 없습니다.');
      history.push(`${PATH.POST}/${postId}`);
    }
  }, [user, author]);

  const onFinishWriting = async (e) => {
    e.preventDefault();

    const prologData = cardRefs.current.map(({ title, content, tags }) => {
      return {
        missionId: missions.find((mission) => mission.name === selectedMission).id,
        title: title.value,
        content: content.getInstance().getMarkdown(),
        tags: tags.map((tag) => ({ name: tag.value })),
      };
    });

    await dispatch(createPost(prologData, accessToken));

    // TODO : fetch hook 통해서 에러처리 선언적으로 해주기
    if (error) {
      alert(error.message);

      return;
    }

    history.push('/');
  };

  return (
    <form onSubmit={onFinishWriting}>
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
