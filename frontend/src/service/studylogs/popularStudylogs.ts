// TODO: 팔레트 색상 추가
const RANDOM_COLOR_PALLETS = [
  '#ff9797',
  '#ff9ebb',
  '#ffcb20',
  '#5ce17d',
  '#a5e1e6',
  '#74bcff',
  '#c886ce',
];

export const getRandomColor = (id: number): string => {
  return RANDOM_COLOR_PALLETS[id % RANDOM_COLOR_PALLETS.length];
};
