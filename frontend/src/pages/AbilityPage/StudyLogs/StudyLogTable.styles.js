import styled from '@emotion/styled';
import { COLOR } from '../../../constants';

export const Section = styled.section`
  && {
    margin-top: 5rem;
  }

  > #studylog-table-title {
    display: inline;
    margin-left: 0.6rem;
    text-align: left;

    font-size: 2rem;
    font-weight: 500;
    color: ${COLOR.BLACK_900};
  }

  > #studylogs-count {
    font-size: 1.4rem;
    margin-left: 1rem;
  }
`;

export const TableRow = styled.tr`
  display: table;
  table-layout: fixed;
  width: 100%;
  height: 4rem;

  font-size: 1.4rem;
`;

export const Thead = styled.thead`
  > tr {
    border: 0.2rem solid ${COLOR.LIGHT_GRAY_500};
    border-radius: 0.5rem;
    background-color: ${COLOR.LIGHT_GRAY_100};

    th {
      font-size: 1.6rem;
      line-height: 1.5;
      font-weight: 500;
    }
  }
`;

export const Tbody = styled.tbody`
  display: block;
  min-height: 10rem;

  tr {
    width: 96%;
    height: 6rem;
    margin: 0 auto;

    border-bottom: 0.1rem solid ${COLOR.LIGHT_GRAY_300};

    position: relative;

    td {
      text-align: left;
    }
  }
`;

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
  padding: 1rem 0;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

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

export const EmptyTableGuide = styled.span`
  display: inline-block;
  width: 100%;
  text-align: center;

  position: absolute;
  bottom: 30%;
`;
