import React, { useState } from 'react';

import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterList } from '../../utils/filterList';
import useReportStudyLogs from '../../hooks/useReportStudyLogs';

import { Button } from '../../components';
import COLOR from '../../constants/color';
import { Checkbox } from './style';
import {
  Section,
  Table,
  TableButtonWrapper,
  Tbody,
  Thead,
  EmptyTableGuide,
} from './ReportStudyLogTable.styles';

const ReportStudyLogTable = ({ onModalOpen, studyLogs, setStudyLogs }) => {
  const [currentStudyLogs, setCurrentStudyLogs] = useReportStudyLogs(studyLogs);

  const [deleteTargets, setDeleteTargets] = useState([]);

  const allChecked = deleteTargets?.length === currentStudyLogs?.length;

  const onToggleAllStudyLog = () => {
    if (allChecked) {
      setDeleteTargets([]);
    } else {
      setDeleteTargets(currentStudyLogs);
    }
  };

  const onToggleStudyLog = (id) => {
    const targetStudyLog = studyLogs.find((studyLog) => studyLog.id === id);

    setDeleteTargets(onToggleCheckbox(deleteTargets, targetStudyLog));
  };

  const onDeleteStudyLogInReport = () => {
    setStudyLogs(filterList(currentStudyLogs, deleteTargets));
    setCurrentStudyLogs((currentList) => filterList(currentList, deleteTargets));
    setDeleteTargets([]);
  };

  return (
    <Section>
      <h3>📚 학습로그 목록</h3>
      <span>
        {deleteTargets?.length ?? 0}개 선택 (총 {currentStudyLogs?.length ?? 0}개)
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
                checked={currentStudyLogs?.length && allChecked}
                disabled={!currentStudyLogs?.length}
              />
            </th>
            <th scope="col">제목</th>
            <th scope="col">역량</th>
          </tr>
        </Thead>

        <Tbody>
          {currentStudyLogs.length !== 0 ? (
            currentStudyLogs.map(({ id, title }) => (
              <tr key={id}>
                <td>
                  <Checkbox
                    type="checkbox"
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
                  <ul>
                    <li></li>
                  </ul>
                  <Button
                    size="XX_SMALL"
                    type="button"
                    css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
                  >
                    +
                  </Button>
                </td>
              </tr>
            ))
          ) : (
            <EmptyTableGuide>'학습로그 불러오기'를 통해 학습로그를 추가해주세요.</EmptyTableGuide>
          )}
        </Tbody>
      </Table>
    </Section>
  );
};

export default ReportStudyLogTable;
