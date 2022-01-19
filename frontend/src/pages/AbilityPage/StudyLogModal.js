import { useEffect, useRef, useState } from 'react';

import useFetch from '../../hooks/useFetch';
import useUserStudyLog from '../../hooks/useUserStudyLogs';
import { requestGetFilters } from '../../service/requests';
import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterIds } from '../../utils/filteringList';
import { COLOR } from '../../constants';
import { Button, Modal, SelectBox } from '../../components';
import { Checkbox } from '../ProfilePageNewReport/style';
import {
  Form,
  TitleContainer,
  SelectBoxContainer,
  StudyLogListContainer,
  StudyLog,
  DeleteGuide,
  ReadMoreButton,
} from './StudyLogModal.styles';

export const SHOW_ALL_STUDYLOGS = {
  id: -1,
  name: '전체보기',
};

const StudyLogModal = ({ onModalClose, username, studyLogs, setStudyLogs }) => {
  /**
   * filters: levels, memebers, missions, tags로 이루어진 객체
   * 이중 levels의 목록에다가 추가로 전체보기 메뉴를 추가한다.
   * 전체보기 메뉴는 어떠한 아이디와도 겹치지 않게 하기 위해서 -1로 지정한다. (SHOW_ALL_STUDYLOGS)
   */
  const [filters] = useFetch([], requestGetFilters);
  const levels = [SHOW_ALL_STUDYLOGS, ...Array.from(filters?.levels ?? [])];

  const [levelName, setLevelName] = useState(SHOW_ALL_STUDYLOGS.name);
  const [selectedStudyLogs, setSelectedStudyLogs] = useState(studyLogs);

  const { studyLogData, setPage } = useUserStudyLog({
    levelId: levels?.find((level) => level.name === levelName)?.id,
    username,
  });
  const { totalSize, totalPage, currPage, data: studyLogsByLevel } = studyLogData;

  const listRef = useRef(null);
  useEffect(() => {
    if (listRef.current) {
      listRef.current.scrollTo({
        top: 0,
        left: 0,
        behavior: 'auto',
      });
    }
  }, [levelName]);

  const selectedStudyLogLength = selectedStudyLogs.length;
  const studyLogLength = studyLogs.length;

  const onSelectStudyLogs = (event) => {
    event.preventDefault();

    setStudyLogs(
      selectedStudyLogs.map(({ id, title }) => {
        if (filterIds(studyLogs).includes(id)) {
          return studyLogs.find((studyLog) => studyLog.id === id);
        } else {
          return { id, title, abilities: [] };
        }
      })
    );
    onModalClose();
  };

  const onToggleStudyLog = (id) => {
    const targetStudyLog = studyLogsByLevel.find((post) => post.id === id);

    setSelectedStudyLogs(onToggleCheckbox(selectedStudyLogs, targetStudyLog));
  };

  const checkTarget = (id) => {
    return filterIds(selectedStudyLogs).includes(id);
  };

  const onGetMoreStudyLog = () => {
    if (currPage >= totalPage) return;

    setPage((page) => page + 1);
  };

  return (
    <Modal width="50%" height="80rem">
      <Form onSubmit={onSelectStudyLogs}>
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
            selectedOption={levelName}
            setSelectedOption={setLevelName}
            title="우아한테크코스 과정 레벨 목록입니다."
            name="레벨"
          />
        </SelectBoxContainer>

        <StudyLogListContainer>
          {totalSize === 0 ? (
            <span>📚 해당 레벨의 학습로그는 총 0개입니다.</span>
          ) : (
            <>
              <span>
                <strong>{levelName}</strong>의 학습로그 총 {totalSize ?? 0}개
              </span>
              <DeleteGuide>이미 등록된 학습로그는 학습로그 목록에서 삭제 가능합니다.</DeleteGuide>

              <ul ref={listRef}>
                {studyLogsByLevel?.map(({ id, mission, title }) => (
                  <StudyLog key={id} isChecked={checkTarget(id)}>
                    <label>
                      <Checkbox
                        type="checkbox"
                        onChange={() => onToggleStudyLog(id)}
                        checked={checkTarget(id)}
                        disabled={filterIds(studyLogs).includes(id)}
                      />
                      <div>
                        <p>{mission.level.name}</p>
                        <h4>{title}</h4>
                      </div>
                    </label>
                  </StudyLog>
                ))}

                {studyLogsByLevel?.length < totalSize && (
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
