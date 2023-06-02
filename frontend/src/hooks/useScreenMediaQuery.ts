import { useEffect, useMemo, useState } from 'react';
import SCREEN_BREAKPOINT from '../constants/screenBreakpoints';

type ScreenBreakPoint = 'xs' | 'sm' | 'md' | 'lg' | 'xl';

type Screen = Record<ScreenBreakPoint, boolean>;

/**
 * React 컴포넌트에서 스크린 사이즈에 따라 실행해야 할 코드가 다를 때
 * 이 훅을 사용하여 분기 처리를 할 수 있다.
 *
 * @example
 * const screen = useScreenMediaQuery();
 * if (screen.sm) { // 모바일 디바이스에서 처리할 로직
 *   // ...
 * }
 * if (screen.xl) {
 *   // ...
 * }
 */
const useScreenMediaQuery = (): Screen => {
  const mediaQueries = useMemo(
    () =>
      Object.fromEntries(
        Object.entries(SCREEN_BREAKPOINT).map(([name, breakpoint]) => [
          name,
          window.matchMedia(`(max-width: ${breakpoint}px)`),
        ])
      ) as Record<ScreenBreakPoint, MediaQueryList>,
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
