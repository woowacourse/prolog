import { NavLink } from 'react-router-dom';
import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/serialize';

import { COLOR } from '../../constants';

export const Container = styled.section<{ css?: SerializedStyles }>`
  width: 100%;
  height: 100%;
  padding: 2rem;
  margin-bottom: 7rem;

  position: relative;

  ${({ css }) => css};
`;

// export const AddNewReportLink = styled(NavLink)<{ css?: SerializedStyles }>`
//   width: fit-content;
//   height: 3rem;
//   margin: 1rem;
//   padding: 0.8rem 2rem;

//   display: flex;
//   align-items: center;
//   z-index: 0;

//   border-radius: 1rem;
//   background-color: ${COLOR.LIGHT_BLUE_800};

//   font-size: 1.3rem;
//   color: ${COLOR.WHITE};

//   ${({ css }) => css};

//   :hover {
//     background-color: ${COLOR.DARK_BLUE_300};
//   }
// `;

export const TimelineWrapper = styled.div`
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

export const Reports = styled.ul``;

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

export const Report = styled.li`
  width: 35rem;
  height: 10rem;
  margin-bottom: 5rem;
  padding: 1rem;
  position: relative;

  border-radius: 2rem;
  background-color: ${COLOR.LIGHT_BLUE_200};
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);

  :first-of-type {
    margin-top: 10rem;
  }

  :nth-of-type(odd) {
    float: right;
    clear: left;
    border-top-left-radius: 0;

    :after {
      left: 0;
      transform: translateX(-4.6rem);
    }
  }

  :nth-of-type(even) {
    float: left;
    clear: right;
    border-top-right-radius: 0;

    :after {
      right: 0;
      transform: translateX(4.6rem);
    }
  }

  :after {
    content: '';
    position: absolute;
    top: 2rem;
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

export const ReportDate = styled.span`
  font-size: 1.2rem;
  font-weight: 500;
  position: absolute;
  top: -2.5rem;
  left: 0;
`;

export const ReportDesc = styled.p`
  margin: 0;

  display: -webkit-box;
  overflow: hidden;
  font-size: 1.2rem;
  text-overflow: ellipsis;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
`;

export const TextButton = styled.button`
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
