const RANDOM_COLOR_PALLETS = [
  '#ff9797',
  '#ffcb20',
  '#bade95',
  '#a5e1e6',
  '#ff9ebb',
  '#74bcff',
  '#c886ce',
  '#9fece0',
  '#a1d9ed',
  '#b3a4d0',
  '#c3a9c9',
  '#f0ec85',
  '#eeb887',
  '#d19191',
  '#dddf95',
  '#d28ab1',
];

export const getRandomColor = (id: number): string => {
  return RANDOM_COLOR_PALLETS[id % RANDOM_COLOR_PALLETS.length];
};
