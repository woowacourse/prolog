import React, { useEffect, useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { SelectBox, Button, BUTTON_SIZE, NewPostCard } from '../../components';
import { nanoid } from 'nanoid';
import { useDispatch, useSelector } from 'react-redux';
import { createPost } from '../../redux/actions/postAction';
import useFetch from '../../hooks/useFetch';
import { requestGetMissions, requestGetTags } from '../../service/requests';
import { SelectBoxWrapper, Post, SubmitButtonStyle } from './styles';
import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '../../constants/message';

const NewPostPage = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const { error } = useSelector((state) => state.post.posts);

  const [postIds] = useState([nanoid()]);
  const [selectedMission, setSelectedMission] = useState('');

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const cardRefs = useRef([]);

  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  useEffect(() => {
    if (error) {
      alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    }
  }, [error]);

  const onFinishWriting = async (e) => {
    e.preventDefault();

    const [prologData] = cardRefs.current.map(({ title, content, tags }) => {
      return {
        missionId: missions.find((mission) => mission.name === selectedMission).id,
        title: title.value,
        content: content.getInstance().getMarkdown(),
        tags: tags?.map((tag) => ({ name: tag.value })) || [],
      };
    });

    if (!prologData.title) {
      alert(ERROR_MESSAGE[2002]);
      return;
    }

    if (!prologData.content) {
      alert(ERROR_MESSAGE[2001]);
      return;
    }

    const isSuccess = await dispatch(createPost([prologData], accessToken));

    if (isSuccess) {
      alert(SUCCESS_MESSAGE.CREATE_POST);
      history.push('/');
    }
  };

  useEffect(() => {
    if (missions.length === 0) return;

    setSelectedMission(missions[0].name);
  }, [missions]);

  return (
    <form onSubmit={onFinishWriting}>
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
      <ul>
        {postIds.map((postId, index) => (
          <Post key={postId}>
            <NewPostCard ref={cardRefs} postOrder={index} tagOptions={tagOptions} />
          </Post>
        ))}
      </ul>

      <Button size={BUTTON_SIZE.SMALL} css={SubmitButtonStyle}>
        작성완료
      </Button>
    </form>
  );
};

export default NewPostPage;
