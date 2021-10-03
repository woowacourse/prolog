import React, { useState } from 'react';

import { onToggleCheckbox } from '../../utils/toggleCheckbox';
import { filterOnlyNewList } from '../../utils/filteringList';
import useReportStudyLogs from '../../hooks/useReportStudyLogs';

import { Button, Pagination } from '../../components';
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
  const { reportStudyLogData, setPage } = useReportStudyLogs(studyLogs);
  const { currPage, totalPage, totalSize, data: currReportStudyLogs } = reportStudyLogData;
  const [deleteTargets, setDeleteTargets] = useState([]);

  const allChecked = deleteTargets?.length === currReportStudyLogs?.length;

  const onToggleAllStudyLog = () => {
    if (allChecked) {
      setDeleteTargets([]);
    } else {
      setDeleteTargets(currReportStudyLogs);
    }
  };

  const onToggleStudyLog = (id) => {
    const targetStudyLog = studyLogs.find((studyLog) => studyLog.id === id);

    setDeleteTargets(onToggleCheckbox(deleteTargets, targetStudyLog));
  };

  const onDeleteStudyLogInReport = () => {
    if (allChecked) {
      const moveTargetPage = currPage === totalPage ? currPage - 1 : currPage;
      setPage(moveTargetPage);
    }

    setStudyLogs((currStudyLogs) => filterOnlyNewList(currStudyLogs, deleteTargets));
    setDeleteTargets([]);
  };

  const onMoveToPage = (number) => {
    setDeleteTargets([]);
    setPage(number);
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
                <ul>
                  <li></li>
                </ul>
                {/* <Button
                    size="XX_SMALL"
                    type="button"
                    css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }}
                  >
                    +
                  </Button> */}
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
