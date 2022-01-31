/** @jsxImportSource @emotion/react */

import { useContext, useEffect, useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { SelectBox, Button, BUTTON_SIZE, NewPostCard } from '../../components';
import { nanoid } from 'nanoid';
import useFetch from '../../hooks/useFetch';
import { requestGetMissions, requestGetTags, requestPostStudylog } from '../../service/requests';
import { SelectBoxWrapper, Post, SubmitButtonStyle } from './styles';
import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '../../constants/message';
import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';
import useMutation from '../../hooks/useMutation';
import ERROR_CODE from '../../constants/errorCode';
import { PATH } from '../../constants';

const NewPostPage = () => {
  const history = useHistory();

  const { user } = useContext(UserContext);

  const { accessToken } = user;

  const [postIds] = useState([nanoid()]);
  const [selectedMission, setSelectedMission] = useState('');

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const cardRefs = useRef([]);

  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  const { mutate: postStudylog } = useMutation(requestPostStudylog, {
    onSuccess: () => {
      alert(SUCCESS_MESSAGE.CREATE_POST);
      history.push(PATH.STUDYLOG);
    },
    onError: (error) => {
      alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
    },
  });

  const onFinishWriting = async (event) => {
    event.preventDefault();

    const [prologData] = cardRefs.current.map(({ title, content, tags }) => {
      return {
        missionId: missions.find((mission) => mission.name === selectedMission).id,
        title: title.value,
        content: content.getInstance().getMarkdown(),
        tags: tags?.map((tag) => ({ name: tag.value })) || [],
      };
    });

    if (!prologData.title) {
      alert(ERROR_MESSAGE[ERROR_CODE.NO_TITLE]);
      return;
    }

    if (!prologData.content) {
      alert(ERROR_MESSAGE[ERROR_CODE.NO_CONTENT]);
      return;
    }

    postStudylog({ data: prologData, accessToken });
  };

  useEffect(() => {
    if (missions.length === 0) return;

    setSelectedMission(missions[0].name);
  }, [missions]);

  return (
    <div css={MainContentStyle}>
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

        <Button size={BUTTON_SIZE.SMALL} cssProps={SubmitButtonStyle}>
          작성완료
        </Button>
      </form>
    </div>
  );
};

export default NewPostPage;
