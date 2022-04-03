import { NavLink } from 'react-router-dom';
import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/serialize';

import { COLOR } from '../../constants';

export const Container = styled.section<{ css?: SerializedStyles }>`
  width: 100%;
  height: 100%;
  margin-bottom: 7rem;
  padding: 2rem;
  position: relative;

  ${({ css }) => css};

  > h2 {
    font-size: 1.8rem;
    text-align: center;
  }
`;

export const AddFirstReportLink = styled(NavLink)<{ css?: SerializedStyles }>`
  width: fit-content;
  height: 3rem;
  margin: 1rem;
  padding: 0.8rem 2rem;

  display: flex;
  align-items: center;
  z-index: 0;

  border-radius: 1rem;
  background-color: ${COLOR.LIGHT_BLUE_800};

  font-size: 1.3rem;
  color: ${COLOR.WHITE};

  ${({ css }) => css};

  :hover {
    background-color: ${COLOR.DARK_BLUE_300};
    color: ${COLOR.WHITE};
  }
`;

export const AddNewReportLink = styled(NavLink)`
  width: 3rem;
  height: 3rem;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: center;
  align-items: center;

  border: 1px solid ${COLOR.DARK_BLUE_900};
  background-color: ${COLOR.DARK_BLUE_900};
  color: ${COLOR.WHITE};
  border-radius: 50%;
  font-size: 2.5rem;

  span {
    padding-bottom: 0.2rem;
  }

  :hover {
    border: 1px solid ${COLOR.DARK_BLUE_900};
    background-color: ${COLOR.LIGHT_BLUE_200};
    color: ${COLOR.DARK_BLUE_900};
  }
`;

/** 타임라인 영역 UI */
export const TimelineWrapper = styled.div`
  margin-top: 5rem;

  /** 가운데 선 */
  :before {
    content: '';
    height: 80%;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    width: 2px;
    background-color: ${COLOR.DARK_BLUE_900};
  }
`;

/** 개별 리포트 */
export const Report = styled.li<{ readOnly?: boolean }>`
  width: 35rem;
  height: 11rem;
  margin-bottom: 5rem;
  padding: 1rem;
  position: relative;

  border-radius: 1.5rem;
  background-color: ${COLOR.LIGHT_BLUE_200};
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);

  :first-of-type {
    margin-top: ${({ readOnly }) => !readOnly && '10rem'};
  }

  :nth-of-type(odd) {
    float: left;
    clear: right;
    border-top-right-radius: 0;

    :after {
      right: 0;
      transform: translateX(4.6rem);
    }
  }

  :nth-of-type(even) {
    float: right;
    clear: left;
    border-top-left-radius: 0;

    :after {
      left: 0;
      transform: translateX(-4.6rem);
    }
  }

  /** 타임라인의 점 */
  :after {
    content: '';
    position: absolute;
    top: ${({ readOnly }) => (readOnly ? '0rem' : '2rem')};
    width: 1.5rem;
    height: 1.5rem;
    border: 1px solid ${COLOR.DARK_BLUE_900};
    border-radius: 50%;
    background-color: ${COLOR.DARK_BLUE_900};
  }

  :hover {
    transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);

    :after {
      border: 1px solid ${COLOR.LIGHT_BLUE_400};
      background-color: ${COLOR.LIGHT_BLUE_400};
    }
  }
`;

/** 리포트 날짜 */
export const ReportDate = styled.span`
  font-size: 1.2rem;
  font-weight: 500;
  position: absolute;
  top: -2.5rem;
  left: 0;
`;

/** 리포트 제목 */
export const ReportTtile = styled.span`
  font-size: 1.4rem;
  font-weight: 500;
  color: ${COLOR.BLACK_900};
`;

/** 리포트 설명 */
export const ReportDesc = styled.p`
  margin: 0;
  display: -webkit-box;

  font-size: 1.2rem;
  text-overflow: ellipsis;
  color: ${COLOR.DARK_GRAY_600};

  overflow: hidden;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
`;

/** 리포트 보러가는 링크 */
export const GoReportLink = styled(NavLink)`
  position: absolute;
  bottom: 1rem;
  font-size: 1.2rem;
  font-weight: 600;

  border: none;

  :hover {
    font-size: 1.3rem;
    color: ${COLOR.RED_900};
  }
`;
