const getGrayscaleColor = (hexColor: string) => {
  const [red, green, blue] = Array.from({ length: 3 }).map((_, index) =>
    Number.parseInt(hexColor.slice(2 * index + 1, 2 * index + 3), 16)
  );
  const grayscaleCode = Math.floor((red + green + blue) / 3).toString(16);

  return '#' + grayscaleCode.repeat(3);
};

export { getGrayscaleColor };
