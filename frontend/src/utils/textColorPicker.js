import { COLOR } from '../constants';

function getTextColor(hexColor) {
  if (!hexColor) {
    return `${COLOR.BLACK_900}`;
  }

  const rgb = parseInt(hexColor.substring(1), 16);

  const r = (rgb >> 16) & 0xff;
  const g = (rgb >> 8) & 0xff;
  const b = (rgb >> 0) & 0xff;

  const luma = 0.2126 * r + 0.7152 * g + 0.0722 * b;

  return luma < 127.5 ? `${COLOR.WHITE}` : `${COLOR.BLACK_900}`;
}

export { getTextColor };
