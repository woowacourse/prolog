/**
 * @description size와 단위를 받아 string으로 반환하는 합수
 * @param size : 크기의 정도를 나타냄.
 * @param unit : 크기의 단위를 나타냄. default 'px'
 * @returns 변환된 크기에 대한 string
 */
export const getSize = (size: number | string, unit = 'px'): string =>
  typeof size === 'number' ? `${size}${unit}` : size;

/**
 * @description 3자리 또는 6자리 hex 코드를 rgba 로 변환하는 코드. 유효하지 않은 hex code가 들어오는 경우 rgba(0,0,0,0) 을 반환함
 * @param hex ex) '#ffffff'
 * @param alpha 0 ~ 1 사이의 소수, 1이상인 경우 1, 0 이하인 경우 0으로 취급함. ex) 0.5
 * @returns ex) 'rgba(255, 255, 255, 0.5)'
 * @todo alpha를 포함하는 hex code에 대한 추가 대응 필요.
 */
export const hexToRgba = (hex: string, alpha: number): string => {
  const isValidHex = /^#([A-Fa-f0-9]{3,4}){1,2}$/.test(hex);

  if (!isValidHex) {
    return 'rgba(0,0,0,0)';
  }

  let color = hex.substring(1).split('');
  if (color.length === 3) {
    color = [color[0], color[0], color[1], color[1], color[2], color[2]];
  }

  const red = parseInt(hex.slice(1, 3), 16);
  const green = parseInt(hex.slice(3, 5), 16);
  const blue = parseInt(hex.slice(5, 7), 16);

  return `rgba(${red}, ${green}, ${blue}, ${alpha > 1 ? 1 : alpha < 0 ? 0 : alpha})`;
};
