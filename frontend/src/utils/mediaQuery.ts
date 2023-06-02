import SCREEN_BREAKPOINT, { ScreenBreakpoint } from '../constants/screenBreakpoints';

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
const mediaQuery = {
  ...(Object.fromEntries(
    Object.entries(SCREEN_BREAKPOINT).map(([breakpoint, maxWidth]) => [
      breakpoint,
      `@media screen and (max-width: ${maxWidth})`,
    ])
  ) as Record<ScreenBreakpoint, string>),
};

export default mediaQuery;
