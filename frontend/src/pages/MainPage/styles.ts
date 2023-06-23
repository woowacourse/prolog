import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Chip } from '../../components';
import { COLOR } from '../../constants';
import MEDIA_QUERY from '../../constants/mediaQuery';

// 메인페이지 전체 적용
export const SectionHeaderGapStyle = css`
  display: flex;
  align-items: center;
  column-gap: 2.8rem;
  row-gap: 2rem;

  margin-bottom: 3.8rem;

  ${MEDIA_QUERY.xs} {
    margin-bottom: 2rem;
  }
`;

// 인기있는 학습로그
export const PopularStudylogListStyle = css`
  display: flex;
  justify-content: center;

  width: 100%;
  height: 32rem;

  gap: 1.6rem;
  position: relative;

  > li {
    width: 250px;

    ${MEDIA_QUERY.md} {
      width: 200px;
    }

    ${MEDIA_QUERY.xs} {
      width: 160px;
    }
  }
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

export const PopularStudylogListButtonIcon = css`
  fill: rgba(0, 0, 0, 0.7);
  width: 20px;
`;
