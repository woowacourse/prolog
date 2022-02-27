/**
 * @description size와 단위를 받아 string으로 반환하는 합수
 * @param size : 크기의 정도를 나타냄.
 * @param unit : 크기의 단위를 나타냄. default 'px'
 * @returns 변환된 크기에 대한 string
 */

export const getSize = (size: number | string, unit = 'px'): string =>
  typeof size === 'number' ? `${size}${unit}` : size;

export default { getSize };
