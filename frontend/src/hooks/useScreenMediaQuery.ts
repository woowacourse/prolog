import { useEffect, useMemo, useState } from 'react';
import SCREEN_BREAKPOINT, { ScreenBreakpoint } from '../constants/screenBreakpoints';

type Screen = Record<`is${Capitalize<ScreenBreakpoint>}`, boolean>;

/**
 * React 컴포넌트에서 스크린 사이즈에 따라 실행해야 할 코드가 다를 때
 * 이 훅을 사용하여 분기 처리를 할 수 있다.
 *
 * @example
 * const { isSm, isXl } = useScreenMediaQuery();
 * if (isSm) { // 모바일 디바이스에서 처리할 로직
 *   // ...
 * }
 * if (isXl) {
 *   // ...
 * }
 */
const useScreenMediaQuery = (): Screen => {
  const mediaQueries = useMemo<Record<`is${Capitalize<ScreenBreakpoint>}`, MediaQueryList>>(
    () => ({
      isXs: window.matchMedia(`(max-width: ${SCREEN_BREAKPOINT.xs}px)`),
      isSm: window.matchMedia(`(max-width: ${SCREEN_BREAKPOINT.sm}px)`),
      isMd: window.matchMedia(`(max-width: ${SCREEN_BREAKPOINT.md}px)`),
      isLg: window.matchMedia(`(max-width: ${SCREEN_BREAKPOINT.lg}px)`),
      isXl: window.matchMedia(`(max-width: ${SCREEN_BREAKPOINT.xl}px)`),
    }),
    []
  );

  const [screen, setScreen] = useState<Screen>(
    Object.fromEntries(
      Object.entries(mediaQueries).map(([name, mediaQuery]) => [name, mediaQuery.matches])
    ) as Screen
  );

  useEffect(() => {
    Object.entries(mediaQueries).map(([name, mediaQuery]) => {
      mediaQuery.addEventListener('change', ({ matches }) => {
        setScreen((screen) => ({ ...screen, [name]: matches }));
      });
    });
  }, []);

  return screen;
};

export default useScreenMediaQuery;
