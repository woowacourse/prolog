export const isEmptyObject = (value: object): boolean =>
  Object.keys(value).length === 0 && value.constructor === Object;
