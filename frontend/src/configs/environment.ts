export const BASE_URL = process.env.REACT_APP_API_URL;

export const APP_MODE = process.env.REACT_APP_MODE;

export const isLocal = process.env.REACT_APP_MODE === 'LOCAL';

export const isDev = process.env.REACT_APP_MODE === 'DEV';

export const isProd = process.env.REACT_APP_MODE === 'PROD';
