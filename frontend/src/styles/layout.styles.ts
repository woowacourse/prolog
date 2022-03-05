import { css } from "@emotion/react";

const DEFAULT_SIZE_UNIT = 'rem'
type CssUnit = 'cm' | 'mm' | 'in' | 'px' | 'pt' | 'pc' | 'em' | 'ex' | 'ch' | 'rem' | 'vw' | 'vh' | 'vmin' | 'vmax' | '%';

/**
 * @param size 크기, number로 오는 경우 댠위(unit)가 적용된 string을 반환
 * @param unit 크기 단위 (default: rem)
 * @returns 단위가 적용된 string
 * @todo string 유효성 검사 추가
 */
const getSize = (size: number | string, unit?: CssUnit): string => typeof size === 'number' ? `${size}${unit ?? DEFAULT_SIZE_UNIT}` : size;

/**
 * 상하 간격
 * @param gap 간격 크기, number로 오는 경우 rem 단위로 변환
 */
export const getRowGapStyle = (gap: number | string) => css`
  > *:not(:last-child) {
    margin-bottom: ${getSize(gap)};
  }
`;

/**
 * 좌우 간격
 * @param gap 간격 크기, number로 오는 경우 rem 단위로 변환
 */
export const getColumnsapStyle = (gap: number | string) => css`
  > *:not(:last-child) {
    margin-right: ${getSize(gap)};
  }
`;