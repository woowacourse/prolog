import { css } from '@emotion/react';
import { getSize } from '../utils/styles';

/**
 * 상하 간격
 * @param gap 간격 크기, number로 오는 경우 rem 단위로 변환
 */
export const getRowGapStyle = (gap: number | string) => css`
  /* > *:not(:last-child) {
    margin-bottom: ${getSize(gap)};
  } */
  row-gap: ${getSize(gap)};
`;

/**
 * 좌우 간격
 * @param gap 간격 크기, number로 오는 경우 rem 단위로 변환
 */
export const getColumnGapStyle = (gap: number | string) => css`
  > *:not(:last-child) {
    margin-right: ${getSize(gap)};
  }
`;
