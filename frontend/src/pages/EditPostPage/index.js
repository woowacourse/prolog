/** @jsxImportSource @emotion/react */

import { useContext, useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { SelectBox, Button, BUTTON_SIZE, EditPostCard } from '../../components';

import useFetch from '../../hooks/useFetch';
import {
  requestEditStudylog,
  requestGetMissions,
  requestGetStudylog,
  requestGetTags,
} from '../../service/requests';
import { ALERT_MESSAGE, ERROR_MESSAGE, PATH } from '../../constants';

import { SelectBoxWrapper, Studylog, SubmitButtonStyle } from '../NewPostPage/styles';
import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';

const EditPostPage = () => {
  const history = useHistory();

  const { user } = useContext(UserContext);
  const { accessToken, username } = user;

  const { id } = useParams();

  const { response: studylog, fetchData: getStudylog } = useRequest({}, () =>
    requestGetStudylog({ id, accessToken })
  );

  const { mutate: editStudylog } = useMutation((data) => requestEditStudylog(data), {
    onSuccess: () => {
      history.goBack();
    },
    onError: (error) => {
      alert(ERROR_MESSAGE[error.code] ?? '글을 수정할 수 없습니다. 다시 시도해주세요');
    },
  });

  const [selectedMission, setSelectedMission] = useState('');
  const cardRefs = useRef([]);

  const [missions] = useFetch([], requestGetMissions);
  const [tags] = useFetch([], requestGetTags);

  const { author, mission } = studylog;
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

    editStudylog({ id, data, accessToken });
  };

  useEffect(() => {
    setSelectedMission(mission?.name);
  }, [mission]);

  useEffect(() => {
    if (author && username !== author.username) {
      alert(ALERT_MESSAGE.CANNOT_EDIT_OTHERS);
      history.push(`${PATH.STUDYLOG}/${id}`);
    }
  }, [username, author]);

  useEffect(() => {
    getStudylog();
  }, [id]);

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
        <Studylog key={id}>
          <EditPostCard ref={cardRefs} studylog={studylog} tagOptions={tagOptions} />
        </Studylog>
        <Button size={BUTTON_SIZE.SMALL} cssProps={SubmitButtonStyle}>
          작성완료
        </Button>
      </form>
    </div>
  );
};

export default EditPostPage;
