import SCREEN_BREAKPOINT, { ScreenBreakpoint } from '../constants/screenBreakpoints';

const mediaQuery = {
  ...(Object.fromEntries(
    Object.entries(SCREEN_BREAKPOINT).map(([breakpoint, maxWidth]) => [
      breakpoint,
      `@media screen and (max-width: ${maxWidth})`,
    ])
  ) as Record<ScreenBreakpoint, string>),
};

export default mediaQuery;
