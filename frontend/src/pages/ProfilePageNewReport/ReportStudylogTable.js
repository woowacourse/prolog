import React, { useEffect, useRef, useState } from 'react';

import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterOnlyNewList } from '../../utils/filteringList';
import useReportStudylogs from '../../hooks/useReportStudylogs';
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
} from './ReportStudylogTable.styles';

const ReportStudylogTable = ({
  onModalOpen,
  studylogs,
  setStudylogs,
  abilities,
  studylogAbilities,
  setStudylogAbilities,
}) => {
  const { reportStudylogData, setPage } = useReportStudylogs(studylogs);
  const { currPage, totalPage, totalSize, data: currReportStudylogs } = reportStudylogData;

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
  const allChecked = deleteTargets?.length === currReportStudylogs?.length;
  const onToggleAllStudylog = () => {
    if (allChecked) {
      setDeleteTargets([]);
    } else {
      setDeleteTargets(currReportStudylogs);
    }
  };

  // 학습로그 목록 체크박스
  const onToggleStudylog = (id) => {
    const targetStudylog = studylogs.find((studylog) => studylog.id === id);

    setDeleteTargets(onToggleCheckbox(deleteTargets, targetStudylog));
  };

  // 학습로그 삭제
  const onDeleteStudylogInReport = () => {
    if (allChecked) {
      const moveTargetPage = currPage === totalPage ? currPage - 1 : currPage;
      setStudylogAbilities([]);
      setPage(moveTargetPage);
    }

    setStudylogs((currStudylogs) => filterOnlyNewList(currStudylogs, deleteTargets));
    setStudylogAbilities((currStudylogAbility) =>
      filterOnlyNewList(currStudylogAbility, deleteTargets)
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
  const selectedAbilities = (studylogId) => {
    const targetStudylogAbilities = studylogAbilities.find(
      (studylogAbility) => studylogAbility.id === studylogId
    );

    return targetStudylogAbilities?.abilities?.map((ability) => (
      <li key={ability.id}>
        <Chip title={ability.name} backgroundColor={ability.color} fontSize="1.2rem">
          {ability.name}
        </Chip>
      </li>
    ));
  };

  // 학습로그 목록에 역량 추가
  const onAddAbilities = (studylogId, currAbility) => {
    const targetStudylogAbility = studylogAbilities?.find(
      (studylogAbility) => studylogAbility.id === studylogId
    );

    if (!targetStudylogAbility) {
      setStudylogAbilities((currStudylog) => [
        ...currStudylog,
        {
          id: studylogId,
          abilities: [currAbility],
        },
      ]);
    } else {
      const index = studylogAbilities
        .map((studylog) => studylog.id)
        .indexOf(targetStudylogAbility.id);

      if (targetStudylogAbility.abilities.find((ability) => ability.id === currAbility.id)) {
        const abilityIndex = targetStudylogAbility.abilities
          .map((ability) => ability.id)
          .indexOf(currAbility.id);

        const deleteStudylogAbility = {
          id: studylogId,
          abilities: [
            ...targetStudylogAbility.abilities.slice(0, abilityIndex),
            ...targetStudylogAbility.abilities.slice(abilityIndex + 1),
          ],
        };

        setStudylogAbilities([
          ...studylogAbilities.slice(0, index),
          deleteStudylogAbility,
          ...studylogAbilities.slice(index + 1),
        ]);
      } else {
        const addStudylogAbiliityResult = {
          id: studylogId,
          abilities: [...targetStudylogAbility.abilities, currAbility],
        };

        setStudylogAbilities([
          ...studylogAbilities.slice(0, index),
          addStudylogAbiliityResult,
          ...studylogAbilities.slice(index + 1),
        ]);
      }
    }
  };

  const isChecked = (studylogId, abilityId) => {
    //studylog에 Ability가 잉ㅆ는가?
    const targetStudylog = studylogAbilities?.find(
      (studylogAbility) => studylogAbility.id === studylogId
    );

    if (!targetStudylog) return false;

    return targetStudylog.abilities.map((ability) => Number(ability.id)).includes(abilityId);
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
          onClick={onDeleteStudylogInReport}
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
                onChange={onToggleAllStudylog}
                checked={currReportStudylogs?.length && allChecked}
                disabled={!currReportStudylogs?.length}
              />
            </th>
            <th scope="col">제목</th>
            <th scope="col">역량</th>
          </tr>
        </Thead>

        <Tbody>
          {currReportStudylogs?.map(({ id, title }) => (
            <tr key={id}>
              <td>
                <Checkbox
                  type="checkbox"
                  value={title | ''}
                  checked={deleteTargets.map((studylog) => studylog.id).includes(id)}
                  onChange={() => onToggleStudylog(id)}
                />
              </td>
              <td>
                <a href={`/studylogs/${id}`} target="_blank" rel="noopener noreferrer">
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
                                <Chip title={ability.name} backgroundColor={ability.color}>
                                  {ability.name}
                                </Chip>
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
      <Pagination dataInfo={reportStudylogData} onSetPage={onMoveToPage} />

      {currReportStudylogs.length === 0 && (
        <EmptyTableGuide>'학습로그 불러오기'를 통해 학습로그를 추가해주세요.</EmptyTableGuide>
      )}
    </Section>
  );
};

export default ReportStudylogTable;
