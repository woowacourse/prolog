export const isEmptyObject = (value: object): boolean =>
  Object.keys(value).length === 0 && value.constructor === Object;

export function getKeyByValue(object: object, value: unknown) {
  return Object.keys(object).find((key) => object[key] === value);
}
