/** @jsxImportSource @emotion/react */

import React, { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';

import { SelectBox, Button, BUTTON_SIZE, EditStudylogCard } from '../../components';

import useFetch from '../../hooks/useFetch';
import useStudyLog from '../../hooks/useStudylog';
import { requestGetMissions, requestGetTags } from '../../service/requests';

import { PATH } from '../../constants';

import { SelectBoxWrapper, Post, SubmitButtonStyle } from '../NewStudylogPage/styles';
import { MainContentStyle } from '../../PageRouter';

const EditStudylogPage = () => {
  const history = useHistory();
  const user = useSelector((state) => state.user.profile.data?.username);
  const accessToken = useSelector((state) => state.user.accessToken.data);

  const { id: studylogId } = useParams();
  const { response: studylog, getData: getStudyLog, editData: editStudylog } = useStudyLog({});

  const [selectedMission, setSelectedMission] = useState('');
  const cardRefs = useRef([]);

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const { id, author, mission } = studylog;
  const tagOptions = tags.map(({ name }) => ({ value: name, label: `#${name}` }));

  const onEditStudylog = async (event) => {
    event.preventDefault();

    const { title, content, tags } = cardRefs.current;
    const data = {
      missionId: missions.find((mission) => mission.name === selectedMission).id,
      title: title.value,
      content: content.getInstance().getMarkdown(),
      tags:
        tags?.map((tag) => ({ name: tag.value })) ||
        studylog.tags.map((tag) => ({ name: tag.name })),
    };

    const hasError = await editStudylog(studylogId, data, accessToken);

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
      history.push(`${PATH.POST}/${studylogId}`);
    }
  }, [user, author]);

  useEffect(() => {
    getStudyLog(studylogId, accessToken);
  }, [studylogId]);

  return (
    <div css={MainContentStyle}>
      <form onSubmit={onEditStudylog}>
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
          <EditStudylogCard ref={cardRefs} post={studylog} tagOptions={tagOptions} />
        </Post>
        <Button size={BUTTON_SIZE.SMALL} cssProps={SubmitButtonStyle}>
          작성완료
        </Button>
      </form>
    </div>
  );
};

export default EditStudylogPage;
