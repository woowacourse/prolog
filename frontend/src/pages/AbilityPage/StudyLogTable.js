import { useEffect, useRef, useState } from 'react';

import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterIds, filterOnlyNewList } from '../../utils/filteringList';
import useReportStudyLogs from '../../hooks/useReportStudyLogs';
import { Button, Chip, Pagination } from '../../components';
import COLOR from '../../constants/color';
import { Checkbox } from '../ProfilePageNewReport/style';
import {
  Section,
  Table,
  TableButtonWrapper,
  Tbody,
  Thead,
  EmptyTableGuide,
  SelectAbilityBox,
} from './StudyLogTable.styles';

const ReportStudyLogTable = ({
  onModalOpen,
  studyLogs: selectedStudyLogs,
  setStudyLogs: setSelectedStudyLogs,
  abilities,
}) => {
  const { selectedStudyLogData, setPage } = useReportStudyLogs(selectedStudyLogs);
  const { currPage, totalPage, totalSize, data: currStudyLogs } = selectedStudyLogData;

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

  // 학습로그 목록 체크박스
  const onToggleStudyLog = (id) => {
    const targetStudyLog = selectedStudyLogs.find((studyLog) => studyLog.id === id);

    setDeleteTargets(onToggleCheckbox(deleteTargets, targetStudyLog));
  };

  // 학습로그 표 전체삭제
  const allChecked = deleteTargets?.length === currStudyLogs?.length;
  const onToggleAllStudyLog = () => {
    if (allChecked) {
      setDeleteTargets([]);
    } else {
      setDeleteTargets(currStudyLogs);
    }
  };

  // 학습로그 삭제
  const onDeleteStudyLogInReport = () => {
    if (allChecked) {
      const moveTargetPage = currPage === totalPage ? currPage - 1 : currPage;
      // setStudyLogAbilities([]);
      setPage(moveTargetPage);
    }

    setSelectedStudyLogs((currStudyLogs) => filterOnlyNewList(currStudyLogs, deleteTargets));
    // setStudyLogAbilities((currStudyLogAbility) =>
    //   filterOnlyNewList(currStudyLogAbility, deleteTargets)
    // );
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

  const onAddAbilities = (studyLogId, currAbility) => {
    if (!selectedStudyLogs) return;

    const targetStudyLog = selectedStudyLogs.find((studyLog) => studyLog.id === studyLogId);
    const targetIndex = filterIds(selectedStudyLogs).indexOf(studyLogId);

    if (!targetStudyLog) return;

    const targetAbilities = targetStudyLog.abilities;
    const newAbilityName = currAbility.name;

    // 역량이 있다면, 해당하는 학습로그 id의 abilities에서 역량의 이름을 뺀다.
    if (targetAbilities.includes(newAbilityName)) {
      const newAbilities = targetAbilities.filter((abilityName) => abilityName !== newAbilityName);

      setSelectedStudyLogs((prevStudyLogs) => [
        ...prevStudyLogs.slice(0, targetIndex),
        {
          id: studyLogId,
          title: targetStudyLog.title,
          abilities: newAbilities,
        },
        ...prevStudyLogs.slice(targetIndex + 1),
      ]);
      // 역량이 없다면, 해당하는 학습로그 id의 abilities에 역량의 이름을 넣는다.
    } else {
      setSelectedStudyLogs((prevStudyLogs) => [
        ...prevStudyLogs.slice(0, targetIndex),
        {
          id: studyLogId,
          title: targetStudyLog.title,
          abilities: [...targetAbilities, newAbilityName],
        },
        ...prevStudyLogs.slice(targetIndex + 1),
      ]);
    }
  };

  // 역량 선택 되었는지
  const isChecked = (studyLogId, abilityName) => {
    if (!selectedStudyLogs) return false;

    const targetStudyLog = selectedStudyLogs.find((studyLog) => studyLog.id === studyLogId);
    if (!targetStudyLog) return false;

    return targetStudyLog.abilities.includes(abilityName);
  };

  // 선택된 역량 추가
  const selectedAbilities = (studyLogId) => {
    const targetAbilities = selectedStudyLogs.find((studyLog) => studyLog.id === studyLogId);

    return targetAbilities?.abilities?.map((ability) => {
      const currentAbility = abilities.find((item) => item.name === ability);

      return (
        <li key={currentAbility.id}>
          <Chip backgroundColor={currentAbility.color} fontSize="1.2rem">
            {currentAbility.name}
          </Chip>
        </li>
      );
    });
  };

  return (
    <Section>
      <h3>📝 학습로그 목록</h3>
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
          css={{ border: `1px solid ${COLOR.DARK_GRAY_800}` }}
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
                checked={currStudyLogs?.length && allChecked}
                disabled={!currStudyLogs?.length}
              />
            </th>
            <th scope="col">제목</th>
            <th scope="col">역량</th>
          </tr>
        </Thead>

        <Tbody>
          {currStudyLogs?.map(({ id, title }) => (
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
                      {abilities?.map((ability) => (
                        <li key={ability.id}>
                          <label>
                            <input
                              type="checkbox"
                              onChange={() => onAddAbilities(id, ability)}
                              checked={isChecked(id, ability.name)}
                            />
                            <Chip backgroundColor={ability.color}>{ability.name}</Chip>
                          </label>
                        </li>
                      ))}
                    </ul>
                  </SelectAbilityBox>
                )}
              </td>
            </tr>
          ))}
        </Tbody>
      </Table>
      <Pagination postsInfo={selectedStudyLogData} onSetPage={onMoveToPage} />

      {currStudyLogs.length === 0 && (
        <EmptyTableGuide>'학습로그 불러오기'를 통해 학습로그를 추가해주세요.</EmptyTableGuide>
      )}
    </Section>
  );
};

export default ReportStudyLogTable;
