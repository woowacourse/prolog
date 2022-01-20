/** @jsxImportSource @emotion/react */

import React, { useEffect, useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';

import { SelectBox, Button, BUTTON_SIZE, NewStudylogCard } from '../../components';

import useFetch from '../../hooks/useFetch';
import useStudylog from '../../hooks/useStudylog';
import { requestGetMissions, requestGetTags } from '../../service/requests';

import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '../../constants/message';

import { SelectBoxWrapper, Post, SubmitButtonStyle } from './styles';
import { MainContentStyle } from '../../PageRouter';
import { PATH } from '../../constants';

// TODO: 이전 한번에 여러개의 학습로그를 작성할 수 있는 부분에서 발생한 레거시.
// 현재 단일 학습로그만 작성. 기획상 동시에 여러건의 학습로그 작성을 없앤 상태로 제거 필요함.
const NewStudylogPage = () => {
  const history = useHistory();

  const accessToken = useSelector((state) => state.user.accessToken.data);

  const [selectedMission, setSelectedMission] = useState('');

  const cardRefs = useRef([]);

  const { success, error, postData: postStudylog } = useStudylog({});

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  useEffect(() => {
    if (error) {
      alert(error);
    }

    if (success) {
      alert(SUCCESS_MESSAGE.CREATE_STUDYLOG);
      history.push(PATH.STUDYLOG);
    }
  }, [success, error]);

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
      alert(ERROR_MESSAGE[2002]);
      return;
    }

    if (!prologData.content) {
      alert(ERROR_MESSAGE[2001]);
      return;
    }

    await postStudylog([prologData], accessToken);
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
          {[0].map((postId, index) => (
            <Post key={postId}>
              <NewStudylogCard ref={cardRefs} postOrder={index} tagOptions={tagOptions} />
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

export default NewStudylogPage;
