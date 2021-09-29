import React, { useState } from 'react';

import useFetch from '../../hooks/useFetch';
import useUserStudyLog from '../../hooks/useUserStudyLogs';
import { requestGetFilters } from '../../service/requests';
import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterIds } from '../../utils/filteringList';

import { COLOR } from '../../constants';
import { Button, Modal, SelectBox } from '../../components';
import { Checkbox } from './style';
import {
  Form,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
  StudyLog,
  DeleteGuide,
  ReadMoreButton,
} from './StudyLogModal.styles';

const StudyLogModal = ({ onModalClose, username, studyLogs, setStudyLogs }) => {
  const [filters] = useFetch([], requestGetFilters);
  const { levels } = filters;

  const [selectedLevelName, setSelectedLevelName] = useState('');
  const [selectedStudyLogs, setSelectedStudyLogs] = useState(studyLogs);

  const { studyLogData, setPage } = useUserStudyLog({
    levelId: levels?.find((level) => level.name === selectedLevelName)?.id,
    username,
  });
  const { totalSize, totalPage, currPage, data: currLevelStudyLogs } = studyLogData;

  const checkTarget = (id) => {
    return filterIds(selectedStudyLogs).includes(id);
  };
  const selectedStudyLogLength = selectedStudyLogs.length;
  const studyLogLength = studyLogs.length;

  const onSelectStudyLogs = (event) => {
    event.preventDefault();

    setStudyLogs(selectedStudyLogs);
    onModalClose();
  };

  const onToggleStudyLog = (id) => {
    const targetStudyLog = currLevelStudyLogs.find((post) => post.id === id);

    setSelectedStudyLogs(onToggleCheckbox(selectedStudyLogs, targetStudyLog));
  };

  const onGetMoreStudyLog = () => {
    if (currPage < totalPage) {
      setPage((page) => page + 1);
    }
  };

  return (
    <Modal width="50%" height="80rem">
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
          {totalSize === 0 ? (
            <span>📚 해당 레벨의 학습로그는 총 0개입니다.</span>
          ) : (
            <>
              <span>
                <strong>{selectedLevelName}</strong>의 학습로그 총 {totalSize ?? 0}개
              </span>
              <DeleteGuide>이미 등록된 학습로그는 학습로그 목록에서 삭제 가능합니다.</DeleteGuide>
              <ul>
                {currLevelStudyLogs?.map(({ id, mission, title }) => (
                  <StudyLog key={id} isChecked={checkTarget(id)}>
                    <label>
                      <Checkbox
                        type="checkbox"
                        checked={checkTarget(id)}
                        onChange={() => onToggleStudyLog(id)}
                        disabled={studyLogs.map((studyLog) => studyLog.id).includes(id)}
                      />
                      <div>
                        <p>{mission.level.name}</p>
                        <h4>{title}</h4>
                      </div>
                    </label>
                  </StudyLog>
                ))}
                {currLevelStudyLogs?.length < totalSize && (
                  <ReadMoreButton type="button" onClick={onGetMoreStudyLog}>
                    더보기
                  </ReadMoreButton>
                )}
              </ul>
            </>
          )}
        </StudyLogListContainer>

        <Button
          size="X_SMALL"
          css={{ backgroundColor: `${COLOR.LIGHT_BLUE_500}` }}
          disabled={selectedStudyLogLength === studyLogLength}
        >
          등록 ({selectedStudyLogLength - studyLogLength}개 선택)
        </Button>
      </Form>
    </Modal>
  );
};

export default StudyLogModal;
