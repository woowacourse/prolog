import React, { useEffect, useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { SelectBox, Button, BUTTON_SIZE, NewPostCard } from '../../components';
import { nanoid } from 'nanoid';
import { useDispatch, useSelector } from 'react-redux';
import { createPost } from '../../redux/actions/postAction';
import useFetch from '../../hooks/useFetch';
import { requestGetMissions, requestGetTags } from '../../service/requests';
import { SelectBoxWrapper, Flex, Post, LogButtonStyle, SubmitButtonStyle } from './styles';

const NewPostPage = () => {
  const dispatch = useDispatch();
  const history = useHistory();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const { error } = useSelector((state) => state.post.posts);

  const [postIds, setPostIds] = useState([nanoid()]);
  const [selectedMission, setSelectedMission] = useState('');

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const cardRefs = useRef([]);

  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  const onFinishWriting = async (e) => {
    e.preventDefault();

    const prologData = cardRefs.current.map(({ title, content, tags }) => {
      return {
        missionId: missions.find((mission) => mission.name === selectedMission).id,
        title: title.value,
        content: content.getInstance().getMarkdown(),
        tags: tags?.map((tag) => ({ name: tag.value })) || [],
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

  useEffect(() => {
    if (missions.length === 0) return;

    setSelectedMission(missions[0].name);
  }, [missions]);

  return (
    <form onSubmit={onFinishWriting}>
      <SelectBoxWrapper>
        <SelectBox
          options={missions?.map((mission) => mission.name)}
          selectedOption={selectedMission}
          setSelectedOption={setSelectedMission}
        />
      </SelectBoxWrapper>
      <ul>
        {postIds.map((postId, index) => (
          <Post key={postId}>
            <NewPostCard ref={cardRefs} postOrder={index} tagOptions={tagOptions} />
          </Post>
        ))}
      </ul>

      {/* <Flex> */}
      {/* <Button
          type="button"
          size={BUTTON_SIZE.LARGE}
          css={LogButtonStyle}
          onClick={() => setPostIds([...postIds, nanoid()])}
        >
          로그추가
        </Button> */}
      <Button size={BUTTON_SIZE.SMALL} css={SubmitButtonStyle}>
        작성완료
      </Button>
      {/* </Flex> */}
    </form>
  );
};

export default NewPostPage;
