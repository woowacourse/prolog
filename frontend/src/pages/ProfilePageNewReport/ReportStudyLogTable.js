import React, { useEffect, useRef, useState } from 'react';

import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterOnlyNewList } from '../../utils/filteringList';
import useReportStudyLogs from '../../hooks/useReportStudyLogs';
import { Button, Chip, Pagination } from '../../components';
import COLOR from '../../constants/color';
import { Checkbox } from './style';
import {
  Section,
  Table,
  TableButtonWrapper,
  Tbody,
  Thead,
  EmptyTableGuide,
  SelectAbilityBox,
} from './ReportStudyLogTable.styles';

const ReportStudyLogTable = ({
  onModalOpen,
  studyLogs,
  setStudyLogs,
  abilities,
  studyLogAbilities,
  setStudyLogAbilities,
}) => {
  const { reportStudyLogData, setPage } = useReportStudyLogs(studyLogs);
  const { currPage, totalPage, totalSize, data: currReportStudyLogs } = reportStudyLogData;

  const [deleteTargets, setDeleteTargets] = useState([]);
  const [selectAbilityBox, setSelectAbilityBox] = useState({
    id: 0,
    state: false,
  });

  const selectAbilityBoxRef = useRef(null);

  // 역량선택 모달 닫기 관련
  useEffect(() => {
    const onCloseOptionList = (event) => {
      if (!selectAbilityBox) return;

      if (!selectAbilityBoxRef.current?.contains(event.target)) {
        setSelectAbilityBox({ ...selectAbilityBox, state: false });
      }
    };

    document.addEventListener('click', onCloseOptionList);

    return () => {
      document.removeEventListener('click', onCloseOptionList);
    };
  }, [selectAbilityBox, selectAbilityBoxRef]);

  // 학습로그 표 전체삭제
  const allChecked = deleteTargets?.length === currReportStudyLogs?.length;
  const onToggleAllStudyLog = () => {
    if (allChecked) {
      setDeleteTargets([]);
    } else {
      setDeleteTargets(currReportStudyLogs);
    }
  };

  // 학습로그 목록 체크박스
  const onToggleStudyLog = (id) => {
    const targetStudyLog = studyLogs.find((studyLog) => studyLog.id === id);

    setDeleteTargets(onToggleCheckbox(deleteTargets, targetStudyLog));
  };

  // 학습로그 삭제
  const onDeleteStudyLogInReport = () => {
    if (allChecked) {
      const moveTargetPage = currPage === totalPage ? currPage - 1 : currPage;
      setStudyLogAbilities([]);
      setPage(moveTargetPage);
    }

    setStudyLogs((currStudyLogs) => filterOnlyNewList(currStudyLogs, deleteTargets));
    setStudyLogAbilities((currStudyLogAbility) =>
      filterOnlyNewList(currStudyLogAbility, deleteTargets)
    );
    setDeleteTargets([]);
  };

  // 페이지 이동
  const onMoveToPage = (number) => {
    setDeleteTargets([]);
    setPage(number);
  };

  // 역량 목록 열기
  const onOpenAbilityBox = (event, id) => {
    event.stopPropagation();

    setSelectAbilityBox({ id, state: true });
  };

  // 선택된 역량 추가
  const selectedAbilities = (studyLogId) => {
    const targetStudyLogAbilities = studyLogAbilities.find(
      (studyLogAbility) => studyLogAbility.id === studyLogId
    );

    return targetStudyLogAbilities?.abilities?.map((ability) => (
      <li key={ability.id}>
        <Chip backgroundColor={ability.color} fontSize="1.2rem">
          {ability.name}
        </Chip>
      </li>
    ));
  };

  // 학습로그 목록에 역량 추가
  const onAddAbilities = (studyLogId, currAbility) => {
    const targetStudyLogAbility = studyLogAbilities?.find(
      (studyLogAbility) => studyLogAbility.id === studyLogId
    );

    if (!targetStudyLogAbility) {
      setStudyLogAbilities((currStudyLog) => [
        ...currStudyLog,
        {
          id: studyLogId,
          abilities: [currAbility],
        },
      ]);
    } else {
      const index = studyLogAbilities
        .map((studyLog) => studyLog.id)
        .indexOf(targetStudyLogAbility.id);

      if (targetStudyLogAbility.abilities.find((ability) => ability.id === currAbility.id)) {
        const abilityIndex = targetStudyLogAbility.abilities
          .map((ability) => ability.id)
          .indexOf(currAbility.id);

        const deleteStudyLogAbility = {
          id: studyLogId,
          abilities: [
            ...targetStudyLogAbility.abilities.slice(0, abilityIndex),
            ...targetStudyLogAbility.abilities.slice(abilityIndex + 1),
          ],
        };

        setStudyLogAbilities([
          ...studyLogAbilities.slice(0, index),
          deleteStudyLogAbility,
          ...studyLogAbilities.slice(index + 1),
        ]);
      } else {
        const addStudyLogAbiliityResult = {
          id: studyLogId,
          abilities: [...targetStudyLogAbility.abilities, currAbility],
        };

        setStudyLogAbilities([
          ...studyLogAbilities.slice(0, index),
          addStudyLogAbiliityResult,
          ...studyLogAbilities.slice(index + 1),
        ]);
      }
    }
  };

  const isChecked = (studyLogId, abilityId) => {
    //studyLog에 Ability가 잉ㅆ는가?
    const targetStudyLog = studyLogAbilities?.find(
      (studyLogAbility) => studyLogAbility.id === studyLogId
    );

    if (!targetStudyLog) return false;

    return targetStudyLog.abilities.map((ability) => Number(ability.id)).includes(abilityId);
  };

  return (
    <Section>
      <h3>📚 학습로그 목록</h3>
      <span>
        {deleteTargets?.length ?? 0}개 선택 (총 {totalSize ?? 0}개)
      </span>
      <TableButtonWrapper>
        <Button
          size="XX_SMALL"
          css={{ backgroundColor: `${COLOR.RED_200}` }}
          type="button"
          onClick={onDeleteStudyLogInReport}
          disabled={!deleteTargets.length}
        >
          삭제
        </Button>
        <Button
          size="XX_SMALL"
          css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
          type="button"
          onClick={onModalOpen}
        >
          학습로그 불러오기
        </Button>
      </TableButtonWrapper>

      <Table>
        <Thead>
          <tr>
            <th scope="col">
              <Checkbox
                type="checkbox"
                onChange={onToggleAllStudyLog}
                checked={currReportStudyLogs?.length && allChecked}
                disabled={!currReportStudyLogs?.length}
              />
            </th>
            <th scope="col">제목</th>
            <th scope="col">역량</th>
          </tr>
        </Thead>

        <Tbody>
          {currReportStudyLogs?.map(({ id, title }) => (
            <tr key={id}>
              <td>
                <Checkbox
                  type="checkbox"
                  value={title | ''}
                  checked={deleteTargets.map((studyLog) => studyLog.id).includes(id)}
                  onChange={() => onToggleStudyLog(id)}
                />
              </td>
              <td>
                <a href={`/posts/${id}`} target="_blank" rel="noopener noreferrer">
                  {title}
                </a>
              </td>
              <td>
                <ul>{selectedAbilities(id)}</ul>
                <Button
                  size="XX_SMALL"
                  type="button"
                  css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
                  onClick={(event) => onOpenAbilityBox(event, id)}
                >
                  +
                </Button>
                {selectAbilityBox.id === id && selectAbilityBox.state && (
                  <SelectAbilityBox ref={selectAbilityBoxRef}>
                    <ul>
                      {abilities?.map(
                        (ability) =>
                          ability.isPresent && (
                            <li key={ability.id}>
                              <label>
                                <input
                                  type="checkbox"
                                  onChange={() => onAddAbilities(id, ability)}
                                  checked={isChecked(id, ability.id)}
                                />
                                <Chip backgroundColor={ability.color}>{ability.name}</Chip>
                              </label>
                            </li>
                          )
                      )}
                    </ul>
                  </SelectAbilityBox>
                )}
              </td>
            </tr>
          ))}
        </Tbody>
      </Table>
      <Pagination postsInfo={reportStudyLogData} onSetPage={onMoveToPage} />

      {currReportStudyLogs.length === 0 && (
        <EmptyTableGuide>'학습로그 불러오기'를 통해 학습로그를 추가해주세요.</EmptyTableGuide>
      )}
    </Section>
  );
};

export default ReportStudyLogTable;
