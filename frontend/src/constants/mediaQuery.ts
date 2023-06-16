import SCREEN_BREAKPOINT, { ScreenBreakpoint } from './screenBreakpoints';

/**
 * css에서 반응형 디자인을 할 때 사용합니다.
 *
 * @example
 * css`
 *   font-size: 24px;
 *   display: grid;
 *   grid-template-columns: repeat(6, 1fr);
 *
 *   ${mediaQuery.md} {
 *     font-size: 20px;
 *     grid-template-columns: repeat(4, 1fr);
 *   }
 *
 *   ${mediaQuery.sm} {
 *     font-size: 16px;
 *     grid-template-columns: repeat(2, 1fr);
 *   }
 * `
 */
const MEDIA_QUERY: Readonly<Record<ScreenBreakpoint, string>> = {
  xs: `@media screen and (max-width: ${SCREEN_BREAKPOINT.xs})`,
  sm: `@media screen and (max-width: ${SCREEN_BREAKPOINT.sm})`,
  md: `@media screen and (max-width: ${SCREEN_BREAKPOINT.md})`,
  lg: `@media screen and (max-width: ${SCREEN_BREAKPOINT.lg})`,
  xl: `@media screen and (max-width: ${SCREEN_BREAKPOINT.xl})`,
};

export default MEDIA_QUERY;
