import { useEffect, useRef, useState } from 'react';

import { Button, Chip, Modal, Pagination } from '../../../components';
import * as Styled from './StudyLogTable.styles.js';
import { COLOR } from '../../../constants';

const ReportStudyLogTable = ({ studyLogs, abilities, setPage, readOnly }) => {
  const currStudyLogs = studyLogs?.data
    ? [...studyLogs.data.map((studylog) => ({ ...studylog, abilities: [] }))]
    : [];

  const wholeAbility = abilities
    ?.map((parentAbility) => [parentAbility, ...parentAbility.children])
    .flat();

  const selectAbilityBoxRef = useRef(null);
  const [selectAbilityBox, setSelectAbilityBox] = useState({
    id: 0,
    isOpen: false,
  });

  // TODO 1. 역량 선택 모달 창을 만든다. -> 모든 역량이 리스트로 되어 있다. 역량 중 선택된 것과 선택되지 않은 것을 표현한다.
  // TODO 2. 역량을 선택하고 확인을 누르면, 테이블에 역량이 적용된다.
  // TODO 3. 각 레벨별로 학습로그를 볼 수 있는 기능을 추가한다.
  // TODO 4. 매핑된 역량은 삭제할 수 없다는 예외사항을 추가한다.
  // TODO 5. prefetch 기능을 사용한다. (20개 정도는 미리 가져와도 될듯..?)

  /** 역량 목록 열기 */
  const onOpenAbilityBox = (event, id) => {
    event.stopPropagation();

    setSelectAbilityBox({ id, isOpen: true });
  };

  /** 역량 선택 닫기 */
  useEffect(() => {
    const onCloseOptionList = (event) => {
      if (!selectAbilityBox) return;

      if (!selectAbilityBoxRef.current?.contains(event.target)) {
        setSelectAbilityBox({ ...selectAbilityBox, isOpen: false });
      }
    };

    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
  }, [selectAbilityBox, selectAbilityBoxRef]);

  /** 선택된 역량을 보여준다.*/
  const selectedAbilities = (studyLogId) => {
    const targetStudyLog = currStudyLogs.find((studyLog) => studyLog.id === studyLogId);

    return wholeAbility?.map((ability) => {
      return (
        <li key={ability.id}>
          <Chip
            backgroundColor={ability.color}
            border={`1px solid ${COLOR.BLACK_OPACITY_500}`}
            fontSize="1.2rem"
            lineHeight="1.6rem"
            // onDelete={onDeleteMappingAbility()}
          >
            {ability.name}
          </Chip>
        </li>
      );
    });
  };

  return (
    <>
      <Styled.Section>
        <h3 id="studylog-table-title">📝 학습로그 목록</h3>
        <span id="studylogs-count">(총 {studyLogs.totalSize ?? 0}개)</span>

        <table>
          <Styled.Thead>
            <Styled.TableRow>
              <th scope="col">제목</th>
              <th scope="col">역량</th>
            </Styled.TableRow>
          </Styled.Thead>

          <Styled.Tbody>
            {currStudyLogs?.map(({ id, title }) => (
              <Styled.TableRow key={id}>
                <Styled.StudyLogTitle>
                  <a href={`/studylogs/${id}`} target="_blank" rel="noopener noreferrer">
                    {title}
                  </a>
                </Styled.StudyLogTitle>

                <Styled.MappedAbility>
                  <ul id="mapped-abilities-list">{selectedAbilities(id)}</ul>

                  {!readOnly && (
                    <Button
                      id="add-ability-button"
                      size="XX_SMALL"
                      type="button"
                      css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
                      onClick={(event) => onOpenAbilityBox(event, id)}
                    >
                      +
                    </Button>
                  )}

                  {/* 
                {selectAbilityBox.id === id && selectAbilityBox.isOpen && (
                  <Styled.SelectAbilityBox ref={selectAbilityBoxRef}>
                    <ul>
                      {wholeAbility?.map((ability) => (
                        <li key={ability.id}>
                          <label>
                            <input
                              type="checkbox"
                              onChange={() => onAddAbilities(id, ability)}
                              checked={isChecked(id, ability)}
                            />
                            <Chip backgroundColor={ability.color} fontSize="1.2rem">
                              {ability.name}
                            </Chip>
                          </label>
                        </li>
                      ))}
                    </ul>
                  </Styled.SelectAbilityBox>
                )} */}
                </Styled.MappedAbility>
              </Styled.TableRow>
            ))}
          </Styled.Tbody>
        </table>
        <Pagination dataInfo={studyLogs} onSetPage={setPage} />

        {currStudyLogs?.length === 0 && (
          <Styled.EmptyTableGuide>등록된 학습로그가 없습니다.</Styled.EmptyTableGuide>
        )}
      </Styled.Section>
      {/* <Modal></Modal> */}
    </>
  );
};

export default ReportStudyLogTable;
