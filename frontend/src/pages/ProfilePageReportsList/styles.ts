import { NavLink } from 'react-router-dom';
import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/serialize';

import { COLOR } from '../../constants';

export const Container = styled.section<{ css?: SerializedStyles }>`
  width: 100%;
  height: fit-content;
  padding: 2rem;
  padding-left: 0;
  margin-bottom: 7rem;

  position: relative;

  border-radius: 1.5rem;
  border: 0.5px solid ${COLOR.LIGHT_GRAY_200};
  background-color: ${COLOR.LIGHT_GRAY_100};

  ${({ css }) => css};
`;

export const AddNewReportLink = styled(NavLink)<{ css?: SerializedStyles }>`
  width: fit-content;
  height: 3rem;
  margin: 1rem;
  padding: 0.8rem 2rem;

  display: flex;
  align-items: center;
  z-index: 0;

  border-radius: 1rem;
  background-color: ${COLOR.DARK_BLUE_800};

  font-size: 1.3rem;
  color: ${COLOR.WHITE};

  ${({ css }) => css};

  :hover {
    background-color: ${COLOR.DARK_BLUE_900};
  }
`;

export const ReportList = styled.ol`
  width: 100%;
  height: fit-content;
  margin-left: 2rem;
  position: relative;

  display: grid;
  grid-template-columns: repeat(2, 1fr);
  align-content: space-between;
  row-gap: 2rem;
`;

export const Card = styled.li`
  width: 38rem;
  height: 27rem;

  position: relative;

  border-radius: 0.5rem;
  background-color: ${COLOR.WHITE};

  word-break: break-all;
  word-wrap: break-word;
  line-height: 1.25;

  :hover {
    box-shadow: 0.5rem 0.5rem 0.5rem ${COLOR.LIGHT_GRAY_200};

    transform: scale(1.02);
    transition: transform 0.1s;

    cursor: pointer;
  }

  a {
    width: 100%;
    height: 100%;
    padding: 1.5rem;

    display: block;

    > p {
      min-height: 3.3rem;
      margin: 1rem 0;

      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;

      font-size: 1.3rem;
      color: ${COLOR.DARK_GRAY_500};
    }
  }

  h4 {
    margin: 1rem 0;

    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;

    font-size: 1.8rem;
  }

  time {
    display: block;
    position: absolute;
    bottom: 1rem;
    right: 1rem;

    text-align: right;
    font-size: 1.2rem;
    color: ${COLOR.DARK_GRAY_600};
  }
`;

export const AbilityList = styled.ul`
  width: 100%;
  max-height: 6rem;
  overflow: hidden;

  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;

  > li {
    max-width: 100%;
    margin: 0.5rem 0;

    div {
      max-width: 100%;

      span {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        font-size: 1.3rem;
        color: ${COLOR.DARK_GRAY_700};
      }
    }
  }
`;

export const StudyLogCount = styled.div`
  margin: 0.5rem;
  margin-top: 1.5rem;

  display: flex;
  position: absolute;
  bottom: 2rem;

  svg {
    width: 2rem;
  }

  && > p {
    margin: 0 1rem;

    font-size: 1.4rem;
    line-height: 2rem;
    color: ${COLOR.BLACK_900};
  }
`;

export const Badge = styled.div`
  width: 10rem;
  height: 10rem;
  overflow: hidden;
  position: absolute;
  top: -1rem;
  right: -1rem;

  ::before,
  ::after {
    content: '';
    display: block;

    position: absolute;

    border: 5px solid ${COLOR.DARK_BLUE_500};
  }

  ::before {
    z-index: 0;
    top: 0;
    left: 0;
  }

  ::after {
    z-index: 0;
    bottom: 0;
    right: 0;
  }

  > span {
    width: 16rem;
    display: block;
    padding: 0.3rem 0;

    position: absolute;
    left: -2rem;
    top: 2.3rem;
    transform: rotate(45deg);

    background-color: ${COLOR.LIGHT_BLUE_800};
    box-shadow: 0 0.5rem 1rem ${COLOR.BLACK_OPACITY_200};
    border: 0.2rem dotted ${COLOR.WHITE};
    outline: 0.4rem solid ${COLOR.LIGHT_BLUE_800};

    color: ${COLOR.WHITE};
    font-size: 1.4rem;
    text-shadow: 0 1px 1px ${COLOR.BLACK_OPACITY_200};
    text-align: center;

    z-index: 1;
  }
`;
