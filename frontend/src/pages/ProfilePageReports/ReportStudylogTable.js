import React from 'react';

import { Chip, Pagination } from '../../components';
import useStudylogsPagination from '../../hooks/useStudylogsPagination';
import { Section, Table, Tbody, Thead, EmptyTableGuide } from './ReportStudylogTable.styles';

const ReportStudylogTable = ({ studylogs }) => {
  const { setPage, reportStudylogData } = useStudylogsPagination(studylogs);
  const { currPage, totalSize, data: currReportStudylogs } = reportStudylogData;

  const onMoveToPage = (number) => {
    setPage(number);
  };

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
            <th scope="col">역량</th>
          </tr>
        </Thead>

        <Tbody>
          {currReportStudylogs.map(({ id, title, abilities }, index) => (
            <tr key={id}>
              <td>
                <span>{(currPage - 1) * 10 + index + 1}</span>
              </td>
              <td>
                <a href={`/studylogs/${id}`} target="_blank" rel="noopener noreferrer">
                  {title}
                </a>
              </td>
              <td>
                <ul>
                  {abilities.map((ability) => (
                    <li key={ability.id}>
                      <Chip title={ability.name} backgroundColor={ability.color} fontSize="1.2rem">
                        {ability.name}
                      </Chip>
                    </li>
                  ))}
                </ul>
              </td>
            </tr>
          ))}
        </Tbody>
      </Table>
      <Pagination dataInfo={reportStudylogData} onSetPage={onMoveToPage} />

      {studylogs.length === 0 && <EmptyTableGuide>등록된 학습로그가 없습니다.</EmptyTableGuide>}
    </Section>
  );
};

export default ReportStudylogTable;
