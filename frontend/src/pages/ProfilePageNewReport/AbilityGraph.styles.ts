import styled from '@emotion/styled';

import { COLOR } from '../../constants';

export const AbilityGraphContainer = styled.div`
  margin: 2rem 0;
  display: flex;
  flex-direction: column;
  align-items: baseline;

  > div {
    display: flex;

    #ability-graph-wrapper {
      width: 50%;
    }

    > table {
      width: 50%;
    }
  }
`;

export const Title = styled.span`
  margin: 0;
  margin-bottom: 0.2rem;
  display: block;

  color: ${COLOR.BLACK_900};
  font-size: 1.6rem;
`;

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
        width: 80%;
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

export const AbilityName = styled.td<{ abilityColor: string }>`
  height: 100%;
  width: 80%;
  padding: 0 1rem;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  border-right: 1px solid ${COLOR.LIGHT_GRAY_500};

  > div {
    display: flex;
    align-items: center;

    span {
      margin: 0 1rem;
      font-size: 1.2rem;
      display: flex;
      align-items: center;
    }

    :before {
      content: '';
      display: block;
      width: 1.5rem;
      height: 1.5rem;

      border: 1px solid ${COLOR.BLACK_900};
      border-radius: 50%;
      ${({ abilityColor }) => abilityColor && `background-color: ${abilityColor}`}
    }
  }
`;

export const AbilityWeight = styled.td`
  > input {
    width: 80%;
    margin: 0 1rem;

    font-size: 1.4rem;
    border: 1px solid ${COLOR.LIGHT_GRAY_500};

    :disabled {
      cursor: not-allowed;
    }
  }
`;
