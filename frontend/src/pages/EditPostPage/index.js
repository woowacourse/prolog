/** @jsxImportSource @emotion/react */

import React, { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { SelectBox, Button, BUTTON_SIZE, EditPostCard } from '../../components';
import { useSelector } from 'react-redux';
import useFetch from '../../hooks/useFetch';
import { requestGetMissions, requestGetTags } from '../../service/requests';
import { PATH } from '../../constants';
import useStudyLog from '../../hooks/useStudyLog';
import { SelectBoxWrapper, Post, SubmitButtonStyle } from '../NewPostPage/styles';
import { MainContentStyle } from '../../PageRouter';

const EditPostPage = () => {
  const history = useHistory();
  const user = useSelector((state) => state.user.profile.data?.username);
  const accessToken = useSelector((state) => state.user.accessToken.data);

  const { id: postId } = useParams();
  const { response: post, getData: getStudyLog, editData: editPost } = useStudyLog({});

  const [selectedMission, setSelectedMission] = useState('');
  const cardRefs = useRef([]);

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const { id, author, mission } = post;
  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

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

    const hasError = await editPost(postId, data, accessToken);

    if (hasError) {
      alert('글을 수정할 수 없습니다. 다시 시도해주세요');

      return;
    }

    history.goBack();
  };

  useEffect(() => {
    setSelectedMission(mission?.name);
  }, [mission]);

  useEffect(() => {
    if (author && user !== author.username) {
      alert('본인이 작성하지 않은 글은 수정할 수 없습니다.');
      history.push(`${PATH.POST}/${postId}`);
    }
  }, [user, author]);

  useEffect(() => {
    getStudyLog(postId, accessToken);
  }, [postId]);

  return (
    <div css={MainContentStyle}>
      <form onSubmit={onEditPost}>
        <SelectBoxWrapper>
          <SelectBox
            options={missions}
            selectedOption={selectedMission}
            setSelectedOption={setSelectedMission}
            title="우아한테크코스 미션 목록입니다."
            name="mission_subjects"
            width="100%"
            maxHeight="25rem"
          />
        </SelectBoxWrapper>
        <Post key={id}>
          <EditPostCard ref={cardRefs} post={post} tagOptions={tagOptions} />
        </Post>
        <Button size={BUTTON_SIZE.SMALL} cssProps={SubmitButtonStyle}>
          작성완료
        </Button>
      </form>
    </div>
  );
};

export default EditPostPage;
