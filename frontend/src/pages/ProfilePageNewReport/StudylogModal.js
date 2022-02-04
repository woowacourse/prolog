import React, { useEffect, useRef, useState } from 'react';

import useFetch from '../../hooks/useFetch';
import useUserStudylog from '../../hooks/useUserStudylogs';
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
  StudylogListContainer,
  Studylog,
  DeleteGuide,
  ReadMoreButton,
} from './StudylogModal.styles';

export const SHOW_ALL_FILTER = {
  id: -1,
  name: '전체보기',
};

const StudylogModal = ({ onModalClose, username, studylogs, setStudylogs }) => {
  const [filters] = useFetch([], requestGetFilters);

  const levels = [SHOW_ALL_FILTER, ...Array.from(filters?.levels ?? [])];

  const [selectedLevelName, setSelectedLevelName] = useState(SHOW_ALL_FILTER.name);
  const [selectedStudylogs, setSelectedStudylogs] = useState(studylogs);

  const { studylogData, setPage } = useUserStudylog({
    levelId: levels?.find((level) => level.name === selectedLevelName)?.id,
    username,
  });

  const listRef = useRef(null);

  useEffect(() => {
    window.scrollTo({ top: 300, behavior: 'smooth' });
  }, []);

  useEffect(() => {
    if (listRef.current) {
      listRef.current.scrollTo({
        top: 0,
        left: 0,
        behavior: 'auto',
      });
    }
  }, [selectedLevelName]);

  const { totalSize, totalPage, currPage, data: currLevelStudylogs } = studylogData;

  const checkTarget = (id) => {
    return filterIds(selectedStudylogs).includes(id);
  };
  const selectedStudylogLength = selectedStudylogs.length;
  const studylogLength = studylogs.length;

  const onSelectStudylogs = (event) => {
    event.preventDefault();

    setStudylogs(selectedStudylogs);
    onModalClose();
  };

  const onToggleStudylog = (id) => {
    const targetStudylog = currLevelStudylogs.find((studylog) => studylog.id === id);

    setSelectedStudylogs(onToggleCheckbox(selectedStudylogs, targetStudylog));
  };

  const onGetMoreStudylog = () => {
    if (currPage < totalPage) {
      setPage((page) => page + 1);
    }
  };

  return (
    <Modal width="50%" height="80rem">
      <Form onSubmit={onSelectStudylogs}>
        <TitleContainer>
          <h2>역량별 학습로그 등록하기</h2>
          <button type="button" onClick={onModalClose}>
            닫기
          </button>
        </TitleContainer>

        <SelectBoxContainer>
          <h3>레벨</h3>
          <SelectBox
            options={levels}
            selectedOption={selectedLevelName}
            setSelectedOption={setSelectedLevelName}
            title="우아한테크코스 과정 레벨 목록입니다."
            name="level"
          />
        </SelectBoxContainer>

        <StudylogListContainer>
          {totalSize === 0 ? (
            <span>📚 해당 레벨의 학습로그는 총 0개입니다.</span>
          ) : (
            <>
              <span>
                <strong>{selectedLevelName}</strong>의 학습로그 총 {totalSize ?? 0}개
              </span>
              <DeleteGuide>이미 등록된 학습로그는 학습로그 목록에서 삭제 가능합니다.</DeleteGuide>
              <ul ref={listRef}>
                {currLevelStudylogs?.map(({ id, mission, title }) => (
                  <Studylog key={id} isChecked={checkTarget(id)}>
                    <label>
                      <Checkbox
                        type="checkbox"
                        checked={checkTarget(id)}
                        onChange={() => onToggleStudylog(id)}
                        disabled={filterIds(studylogs).includes(id)}
                      />
                      <div>
                        <p>{mission.level.name}</p>
                        <h4>{title}</h4>
                      </div>
                    </label>
                  </Studylog>
                ))}
                {currLevelStudylogs?.length < totalSize && (
                  <ReadMoreButton type="button" onClick={onGetMoreStudylog}>
                    더보기
                  </ReadMoreButton>
                )}
              </ul>
            </>
          )}
        </StudylogListContainer>

        <Button
          size="X_SMALL"
          css={{ backgroundColor: `${COLOR.LIGHT_BLUE_500}` }}
          disabled={selectedStudylogLength === studylogLength}
        >
          등록 ({selectedStudylogLength - studylogLength}개 선택)
        </Button>
      </Form>
    </Modal>
  );
};

export default StudylogModal;
