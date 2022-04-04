import styled from '@emotion/styled';
import { COLOR } from '../../constants';

/** 테이블 관련 UI */
export const Thead = styled.thead`
  width: 50%;

  > tr {
    background-color: ${COLOR.LIGHT_GRAY_100};
    border: 0.1rem solid ${COLOR.LIGHT_GRAY_500};
    border-style: list-styled;

    th {
      font-size: 1.3rem;
      line-height: 1.5;
      font-weight: 500;

      :first-of-type {
        width: 50%;
        border-right: 1px solid ${COLOR.LIGHT_GRAY_500};
      }
    }
  }
`;

export const TableRow = styled.tr`
  width: 100%;
  height: 3rem;
  display: table;
  table-layout: fixed;
`;

export const Tbody = styled.tbody`
  display: block;
  min-height: 10rem;

  tr {
    width: 100%;
    height: 4rem;
    margin: 0 auto;

    border-bottom: 0.1rem solid ${COLOR.LIGHT_GRAY_300};

    position: relative;

    td {
      text-align: left;
    }
  }
`;

/** 학습로그 테이블 관련 UI */
export const StudyLogTitle = styled.td`
  height: 100%;
  width: 50%;
  padding-right: 5rem;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;

  font-weight: 500;
  font-size: 1.6rem;

  a {
    :hover {
      text-decoration: underline;
    }
  }
`;

export const MappedAbility = styled.td`
  padding: 1rem;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;

  > #mapped-abilities-list {
    width: 95%;

    li {
      display: inline-table;
      margin: 0.2rem 0;
    }
  }

  button {
    width: 10%;
    margin-top: 0.1rem;
    color: ${COLOR.DARK_GRAY_900};
    font-size: 2rem;
  }
`;
