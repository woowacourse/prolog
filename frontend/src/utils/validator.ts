export const limitLetterLength = (words: string, limitedLength: number) => {
  if (words.length > limitedLength) return false;

  return true;
};
