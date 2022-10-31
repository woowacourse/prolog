type ValueOf<T> = T[keyof T];
type Nullable<T> = { [P in keyof T]: T[P] | null };

export type { ValueOf, Nullable };
