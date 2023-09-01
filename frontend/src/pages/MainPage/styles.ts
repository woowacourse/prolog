import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Chip } from '../../components';
import { COLOR } from '../../constants';
import mediaQuery from '../../constants/mediaQuery';

// 메인페이지 전체 적용
export const SectionHeaderGapStyle = css`
  display: flex;
  align-items: center;
  gap: 2.8rem;
  padding-left: 1.2rem;
  margin-bottom: 1.2rem;

  ${mediaQuery.xs} {
    padding-left: 0.8rem;
    margin-bottom: 0.4rem;
  }
`;

// 인기있는 학습로그
export const PopularStudylogListStyle = css`
  width: 100%;
  height: 32rem;

  display: flex;
  justify-content: content;
  align-items: center;

  position: relative;

  > *:not(:last-child) {
    margin-right: 1.6rem;
  }

  > *:last-child {
    margin-right: 0.2rem;
  }

  overflow-x: scroll;
`;

export const PopularStudylogListRightControlStyle = css`
  width: 3.6rem;
  height: 300px;

  background-color: rgba(0, 0, 0, 0);

  position: absolute;
  bottom: 0;
  right: 0;

  display: flex;
  align-items: center;

  button {
    width: 3.2rem;
    height: 3.2rem;

    margin-right: auto;

    background-color: rgba(0, 0, 0, 0.7);
    border-radius: 50%;

    color: white;

    opacity: 0;
  }

  :hover {
    button {
      opacity: 1;
    }
  }
`;

export const StyledChip = styled(Chip)<{ active: boolean }>`
  cursor: pointer;
  padding: 7px 22px;
  background-color: ${({ active }) => active && COLOR.LIGHT_BLUE_300};
`;
