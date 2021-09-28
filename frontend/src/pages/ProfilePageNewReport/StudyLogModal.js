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
  DeleteGuide,
  ReadMoreButton,
} from './StudyLogModal.styles';

const StudyLogModal = ({ onModalClose, username, studyLogs, setStudyLogs }) => {
  const [filters] = useFetch([], requestGetFilters);
  const { levels } = filters;

  const { selectedLevelName, setSelectedLevelName, postData, setPage } = useUserStudyLog({
    levels,
    username,
  });
  const { totalSize, totalPage, currPage, data: posts } = postData;

  const [selectedStudyLogs, setSelectedStudyLogs] = useState(studyLogs);

  const onSelectStudyLogs = (event) => {
    event.preventDefault();

    setStudyLogs(selectedStudyLogs);
    onModalClose();
  };

  const onToggleStudyLog = (id) => {
    const targetStudyLog = posts.find((post) => post.id === id);

    setSelectedStudyLogs(onToggleCheckbox(selectedStudyLogs, targetStudyLog));
  };

  const checkTarget = (id) => {
    return selectedStudyLogs.map((studyLog) => studyLog.id).includes(id);
  };

  const onGetMoreStudyLog = () => {
    if (currPage < totalPage) {
      setPage((page) => page + 1);
    }
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
          {totalSize === 0 ? (
            <span>📚 해당 레벨의 학습로그는 총 0개입니다.</span>
          ) : (
            <>
              <span>📚 해당 레벨의 학습로그는 총 {totalSize ?? 0}개입니다.</span>
              <DeleteGuide>
                이미 등록된 학습로그는 리포트 수정 페이지에서 삭제 가능합니다.
              </DeleteGuide>
              <ul>
                {posts?.map(({ id, mission, title }) => {
                  const checked = checkTarget(id);

                  return (
                    <StudyLog key={id} isChecked={checked}>
                      <label>
                        <Checkbox
                          type="checkbox"
                          checked={checked}
                          onChange={() => onToggleStudyLog(id)}
                          disabled={studyLogs.map((studyLog) => studyLog.id).includes(id)}
                        />
                        <div>
                          <p>{mission.level.name}</p>
                          <h4>{title}</h4>
                        </div>
                      </label>
                    </StudyLog>
                  );
                })}
                {posts?.length < totalSize && (
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
          disabled={selectedStudyLogs.length === studyLogs.length}
        >
          등록 ({selectedStudyLogs.length - studyLogs.length}개 선택)
        </Button>
      </Form>
    </Modal>
  );
};

export default StudyLogModal;
