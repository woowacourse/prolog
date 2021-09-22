import React, { useState } from 'react';

import useFetch from '../../hooks/useFetch';
import useUserStudyLog from '../../hooks/useUserStudyLogs';
import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { requestGetFilters } from '../../service/requests';

import { Button, Modal, SelectBox } from '../../components';
import { COLOR } from '../../constants';
import { Checkbox } from './style';
import {
  Form,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
  StudyLog,
} from './StudyLogModal.styles';

const StudyLogModal = ({ onModalClose, username, studyLogs, setStudyLogs }) => {
  const [filters] = useFetch([], requestGetFilters);
  const levels = filters.levels;
  const { selectedLevelName, setSelectedLevelName, posts } = useUserStudyLog({ levels, username });

  const [selectedStudyLogs, setSelectedStudyLogs] = useState(studyLogs);

  const onSelectStudyLogs = (event) => {
    event.preventDefault();

    setStudyLogs(selectedStudyLogs);
    onModalClose();
  };

  const onToggleStudyLog = (id) => {
    setSelectedStudyLogs(onToggleCheckbox(selectedStudyLogs, id));
  };

  return (
    <Modal width="50%" height="80%">
      <Form onSubmit={onSelectStudyLogs}>
        <TitleContainer>
          <h2 id="dialog1Title">역량별 학습로그 등록하기</h2>
          <button type="button" onClick={onModalClose}>
            닫기
          </button>
        </TitleContainer>

        <SelectBoxContainer>
          <h3>레벨</h3>
          <SelectBox
            options={levels?.map((level) => level.name)}
            selectedOption={selectedLevelName}
            setSelectedOption={setSelectedLevelName}
            title="우아한테크코스 과정 레벨 목록입니다."
            name="level"
          />
        </SelectBoxContainer>

        <StudyLogListContainer>
          {posts.length === 0 ? (
            <p>해당 레벨의 학습로그가 없습니다.</p>
          ) : (
            <ul>
              <span>
                ✅ {posts.filter((post) => selectedStudyLogs.includes(post.id)).length}개 선택 (총{' '}
                {posts.length}개)
              </span>

              {posts.map(({ id, mission, title }) => (
                <StudyLog key={id} isChecked={selectedStudyLogs.includes(id)}>
                  <label>
                    <Checkbox
                      type="checkbox"
                      checked={selectedStudyLogs.includes(id)}
                      onChange={() => onToggleStudyLog(id)}
                    />
                    <div>
                      <p>{mission.level.name}</p>
                      <h4>{title}</h4>
                    </div>
                  </label>
                </StudyLog>
              ))}
            </ul>
          )}
        </StudyLogListContainer>

        <Button size="X_SMALL" css={{ backgroundColor: `${COLOR.LIGHT_BLUE_500}` }}>
          등록
        </Button>
      </Form>
    </Modal>
  );
};

export default StudyLogModal;
