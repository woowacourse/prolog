import React from 'react';

import { Button } from '../../components';
import COLOR from '../../constants/color';
import { Section, Table, TableButtonWrapper, Tbody, Thead } from './ReportStudyLogTable.styles';
import { Checkbox } from './style';

const ReportStudyLogTable = () => {
  return (
    <Section>
      <h2>📚 학습로그 목록</h2>
      <span>2개 선택 (총 23개)</span>
      <TableButtonWrapper>
        <Button size="XX_SMALL" css={{ backgroundColor: `${COLOR.RED_200}` }} type="button">
          삭제
        </Button>
        <Button size="XX_SMALL" css={{ backgroundColor: `${COLOR.LIGHT_BLUE_300}` }} type="button">
          학습로그 불러오기
        </Button>
      </TableButtonWrapper>

      <Table>
        <Thead>
          <tr>
            <th scope="col">
              <Checkbox type="checkbox" />
            </th>
            <th scope="col">제목</th>
            <th scope="col">역량</th>
          </tr>
        </Thead>

        <Tbody>
          {Array.from({ length: 10 }).map(() => (
            <tr key={Math.random()}>
              <td>
                <Checkbox type="checkbox" />
              </td>
              <td>자바스크립트 비동기</td>
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
          ))}
        </Tbody>
      </Table>
    </Section>
  );
};

export default ReportStudyLogTable;
