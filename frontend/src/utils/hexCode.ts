export const isCorrectHexCode = (str: string) => {
  const regex = /^#([0-9a-f]{3}){1,2}$/i;

  return regex.test(str);
};
