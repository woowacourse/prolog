import { css } from '@emotion/react';
import styled from '@emotion/styled';
import COLOR from '../../constants/color';

const countColor = {
  0: 'transparent',
  1: '#1ea7fd1a',
  2: '#1ea7fd3a',
  3: '#1ea7fd5a',
};

const Container = styled.div`
  min-width: 30rem;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 3rem;

  & svg {
    vertical-align: middle;
  }

  & > button:first-of-type svg {
    transform: rotate(180deg);
  }
`;

const Year = styled.div`
  text-align: center;
  font-size: 1rem;
  line-height: 1;
`;

const Month = styled.div`
  text-align: center;
  font-size: 2.5rem;
  line-height: 1;
`;

const CalendarWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 22rem;
`;

const WeekWrapper = styled.ul`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 5rem;
`;

const Week = styled.strong`
  font-size: 1.4rem;

  ${({ isSunday, isSaturday }) => css`
    ${isSunday &&
    css`
      color: #ff0000;
    `}

    ${isSaturday &&
    css`
      color: #1ea7fd;
    `}
  `}
`;

const DayWrapper = styled.ul`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  width: 100%;
  height: 100%;
`;

const ListItem = styled.li`
  position: relative;
  width: calc(100% / 7);
  text-align: center;
`;

const Day = styled.button`
  position: relative;
  user-select: none;
  cursor: pointer;
  font-size: inherit;
  font-size: 1.4rem;

  ${({ isSunday, isSaturday, isHover, isSelected, count }) => css`
    ${isHover &&
    css`
      &::after {
        position: absolute;
        content: '';
        width: 3rem;
        height: 0.5rem;
        transform: translateX(-50%);
        left: 50%;
        bottom: -0.5rem;
        background-color: #1ea7fd8a;
      }
    `}

    &::before {
      position: absolute;
      content: '';
      width: 2.5rem;
      height: 2.5rem;
      transform: translate(-50%, -50%);
      left: 50%;
      top: 50%;
      border-radius: 50%;
      background-color: ${count < 4 ? countColor[count] : countColor[3]};
    }

    ${isSunday &&
    css`
      color: #ff0000;
    `}

    ${isSaturday &&
    css`
      color: #1ea7fd;
    `}

    ${isSelected &&
    css`
      &::after {
        position: absolute;
        content: '';
        width: 3rem;
        height: 0.5rem;
        transform: translateX(-50%);
        left: 50%;
        bottom: -0.5rem;
        background-color: #1ea7fd;
      }
    `}
  `}
`;

const TitleList = styled.div`
  position: absolute;
  top: -0.5rem;
  border: 1px solid #ccc;
  border-radius: 10px;
  min-width: 15rem;
  max-width: 20rem;
  padding: 0.5rem;
  z-index: 1;
  background-color: white;
  text-align: left;
  font-size: 1.4rem;

  ${({ isRight }) =>
    isRight
      ? css`
          right: 6.5rem;
          box-shadow: rgba(99, 99, 99, 0.3) 1px 2px 8px 0;
        `
      : css`
          left: 6.5rem;
          box-shadow: rgba(99, 99, 99, 0.3) -1px 2px 8px 0;
        `}

  & > div {
    margin: 0.5rem;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
`;

const MoreTitle = styled.div`
  font-size: 1.2rem;
  color: ${COLOR.LIGHT_GRAY_800};
  /* font-weight: 300; */
`;

export {
  Container,
  Header,
  Year,
  Month,
  CalendarWrapper,
  WeekWrapper,
  ListItem,
  Week,
  DayWrapper,
  Day,
  TitleList,
  MoreTitle,
};
