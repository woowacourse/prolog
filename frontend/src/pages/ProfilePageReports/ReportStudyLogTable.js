import React from 'react';

import { Section, Table, Tbody, Thead, EmptyTableGuide } from './ReportStudyLogTable.styles';

const ReportStudyLogTable = ({ studyLogs }) => {
  return (
    <Section>
      <h3>📚 학습로그 목록</h3>
      <span>(총 {studyLogs.length ?? 0}개)</span>

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
          {studyLogs?.map(({ id, title, abilities }, index) => (
            <tr key={id}>
              <td>
                <span>{index + 1}</span>
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
      {/* <Pagination postsInfo={reportStudyLogData} onSetPage={onMoveToPage} /> */}

      {studyLogs.length === 0 && <EmptyTableGuide>등록된 학습로그가 없습니다.</EmptyTableGuide>}
    </Section>
  );
};

export default ReportStudyLogTable;
