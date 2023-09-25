export type HSL = readonly [number, number, number];

export const hsl = ([hue, saturation, lightness]: HSL): string =>
  `hsl(${hue}, ${saturation * 100}%, ${lightness * 100}%)`;

export const toHue = ([, saturation, lightness]: HSL, hue: number): HSL => [
  hue,
  saturation,
  lightness,
];

export const toSaturation = ([hue, , lightness]: HSL, saturation: number): HSL => [
  hue,
  saturation,
  lightness,
];

export const toLightness = ([hue, saturation]: HSL, lightness: number): HSL => [
  hue,
  saturation,
  lightness,
];

export const toAdjustedLightness = ([hue, saturation, lightness]: HSL, offset: number): HSL => [
  hue,
  saturation,
  lightness + offset,
];

export const KeywordColors = {
  MAIN_KEYWORD: [220, 0.5, 0.4],
  SUB_KEYWORD: [220, 1, 0.8],
  LINE: [0, 0, 0.2],
  BORDER: [0, 0, 1],
} as const;

export const ImportanceColors = {
  1: [0, 0, 0.93],
  2: [60, 1, 0.48],
  3: [30, 1, 0.6],
  4: [0, 1, 0.5],
  /**
   * 임시 색상
   * 원래 중요도는 1~4로 1이 가장 안 중요하고, 4가 가장 중요.
   * 현재 백엔드에 importance:5 데이터가 남아있어 해당 값을 임시로 무효하기 위한 색상
   * */
  5: [0, 0, 0.93],
} as const;
