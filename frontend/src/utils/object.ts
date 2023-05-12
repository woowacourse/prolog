export const isEmptyObject = (value: object): boolean =>
  Object.keys(value).length === 0 && value.constructor === Object;

export function getKeyByValue<Object extends object>(
  object: Object,
  value: Object[keyof Object]
): keyof Object {
  return Object.keys(object).find((key) => object[key] === value) as keyof Object;
}
