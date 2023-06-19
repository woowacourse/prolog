export type ScreenBreakpoint = 'xs' | 'sm' | 'md' | 'lg' | 'xl';

const SCREEN_BREAKPOINT = {
  xs: 420,
  sm: 640,
  md: 768,
  lg: 1024,
  xl: 1280,
} as const;

export default SCREEN_BREAKPOINT;
