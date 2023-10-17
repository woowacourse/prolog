import { css, Global } from '@emotion/react';

const RoadmapStyles = () => {
  return (
    <Global
      styles={css`
        @font-face {
          font-family: 'TheJamsil5Bold';
          src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2')
            format('woff2');
          font-weight: 700;
          font-style: normal;
        }
      `}
    />
  );
};

export default RoadmapStyles;
