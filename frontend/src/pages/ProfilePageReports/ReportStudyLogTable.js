import React from 'react';

import { Pagination } from '../../components';
import useStudyLogsPagination from '../../hooks/useStudyLogsPagination';
import { Section, Table, Tbody, Thead, EmptyTableGuide } from './ReportStudyLogTable.styles';

const ReportStudyLogTable = ({ studyLogs }) => {
  const { setPage, reportStudyLogData } = useStudyLogsPagination(studyLogs);
  const { currPage, totalSize, data: currReportStudyLogs } = reportStudyLogData;

  const onMoveToPage = (number) => setPage(number);

  return (
    <Section>
      <h3>📚 학습로그 목록</h3>
      <span>(총 {totalSize ?? 0}개)</span>

      <Table>
        <Thead>
          <tr>
            <th scope="col">
              <span>번호</span>
            </th>
            <th scope="col">제목</th>
          </tr>
        </Thead>

        <Tbody>
          {currReportStudyLogs.map(({ id, title }, index) => (
            <tr key={id}>
              <td>
                <span>{(currPage - 1) * 10 + index + 1}</span>
              </td>
              <td>
                <a href={`/posts/${id}`} target="_blank" rel="noopener noreferrer">
                  {title}
                </a>
              </td>
            </tr>
          ))}
        </Tbody>
      </Table>
      <Pagination postsInfo={reportStudyLogData} onSetPage={onMoveToPage} />

      {studyLogs.length === 0 && <EmptyTableGuide>등록된 학습로그가 없습니다.</EmptyTableGuide>}
    </Section>
  );
};

export default ReportStudyLogTable;
