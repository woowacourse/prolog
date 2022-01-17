import styled from '@emotion/styled';

import COLOR from '../../constants/color';

const Section = styled.section`
  && {
    margin-top: 5rem;
    position: relative;
  }

  > h3 {
    display: inline;
    margin-left: 0.6rem;
    margin-bottom: 0.3rem;
    text-align: left;

    font-size: 1.8rem;
    font-weight: 400;
    color: ${COLOR.BLACK_900};
  }

  > span {
    font-size: 1.4rem;
    margin-left: 1rem;
  }

  > div > button {
    display: inline;
    width: fit-content;
    line-height: 1;
    padding: 0.5rem 1rem;
    margin-right: 0.5rem;

    font-size: 1.4rem;
  }
`;

const TableButtonWrapper = styled.div`
  position: absolute;
  right: 0;
  top: -0.5rem;

  > button {
    padding: 0.5rem 1.5rem;
    border-radius: 0.8rem;
    font-size: 1.4rem;

    :not(:disabled):hover {
      filter: brightness(0.9);
    }
  }
`;

const Table = styled.table`
  width: 100%;

  thead {
    tr {
      border: 0.2rem solid ${COLOR.LIGHT_GRAY_500};
      border-radius: 0.5rem;

      background-color: ${COLOR.LIGHT_GRAY_100};
    }
  }

  tr {
    display: table;
    table-layout: fixed;
    width: 100%;
    height: 4rem;

    font-size: 1.4rem;
  }

  th {
    font-size: 1.6rem;
    line-height: 1.5;
    font-weight: 500;
  }

  td {
    text-align: left;
    padding-left: 1rem;
  }
`;

const Thead = styled.thead`
  > tr {
    th:nth-of-type(1) {
      width: 10%;
      text-align: center;
    }

    th:nth-of-type(2) {
      width: 30%;
    }

    th:nth-of-type(3) {
      width: 60%;
    }
  }
`;

const Tbody = styled.tbody`
  display: block;
  min-height: 30rem;

  tr {
    width: 96%;
    border-bottom: 0.1rem solid ${COLOR.LIGHT_GRAY_300};
    height: 6rem;
    margin: 0 auto;

    position: relative;

    td:nth-of-type(1) {
      height: 100%;
      width: 6%;
      text-align: center;
    }

    td:nth-of-type(2) {
      height: 100%;
      width: 47%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      padding-right: 0.6rem;

      a {
        :hover {
          text-decoration: underline;
        }
      }
    }

    td:nth-of-type(3) {
      height: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;

      > ul {
        width: 90%;
        height: 100%;
        margin-right: 1.5rem;
        overflow: auto;

        display: flex;
        align-items: center;

        li {
          height: fit-content;
        }
      }

      button {
        width: 10%;
        margin-top: 0.1rem;
        color: ${COLOR.DARK_GRAY_900};
        font-size: 2rem;
      }
    }
  }
`;

const SelectAbilityBox = styled.div`
  width: 20rem;
  height: 24rem;

  position: absolute;
  top: 3rem;
  right: 1rem;

  border: 1px solid ${COLOR.LIGHT_GRAY_800};
  background-color: ${COLOR.WHITE};
  border-radius: 1rem;

  z-index: 2;

  ul {
    width: 100%;
    height: 100%;
    padding: 1rem;
    overflow-y: auto;

    display: flex;
    flex-direction: column;

    li {
      width: 100%;
      padding: 1rem 0;

      border-bottom: 1px solid ${COLOR.LIGHT_GRAY_200};

      label {
        width: 100%;

        display: flex;
        align-items: center;
      }

      input {
        margin-right: 1rem;
      }
    }
  }
`;

const EmptyTableGuide = styled.span`
  display: inline-block;
  width: 100%;
  text-align: center;

  position: absolute;
  bottom: 30%;
`;

export { Section, TableButtonWrapper, Table, Thead, Tbody, SelectAbilityBox, EmptyTableGuide };
