export const arrayMutation = <T extends Readonly<unknown[]>>(arr: T, method: keyof T) => {
  switch (method) {
    case 'includes':
      return (arg: unknown): boolean => arr.includes(arg);
    default:
      throw new Error('Invalid method');
  }
};
