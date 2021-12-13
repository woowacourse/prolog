/**
 * isCorrectHexCode 함수
 * 사용자가 입력한 hexCode가 올바른 hexCode인지 유효성을 검증한다.
 *
 * @param {string} str - 사용자가 입력한 hexCode
 * @returns boolean
 */
export const isCorrectHexCode = (str: string) => {
  const regex = /^#([0-9a-f]{3}){1,2}$/i;

  return regex.test(str);
};
