import { css, Global } from '@emotion/react';
import COLOR from './constants/color';
import mediaQuery from './constants/mediaQuery';

const GlobalStyles = () => (
  <Global
    styles={css`
      @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap');

      @font-face {
        font-family: 'BMHANNAPro';
        src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff')
          format('woff');
        font-weight: normal;
        font-style: normal;
      }

      * {
        box-sizing: border-box;
      }

      html {
        font-size: 10px;
      }

      body {
        margin: 0;
        padding: 0;
        font-size: 1.6rem;
        min-height: 100vh;
        width: 100%;
        background-color: ${COLOR.LIGHT_GRAY_50};
        color: ${COLOR.DARK_GRAY_900};
      }

      #root {
        font-family: 'Noto Sans KR', sans-serif;
        height: 100%;
      }

      ul,
      ol {
        list-style: none;
        margin: 0;
        padding: 0;
      }

      a {
        text-decoration: none;
        outline: none;
        color: ${COLOR.BLACK_900};
        &:hover,
        &:active,
        &:visited,
        &:focus {
          text-decoration: none;
          color: inherit;
          font-weight: 600;
        }
      }

      input {
        font-family: inherit;
      }

      button {
        padding: 0;
        outline: none;
        border: none;
        background: none;
        cursor: pointer;
        font-family: inherit;
        &[disabled] {
          cursor: not-allowed;
        }
      }

      h1,
      h2,
      h3,
      h4,
      h5,
      h6 {
        margin: 0;
      }

      ${mediaQuery.xs} {
        html {
          font-size: 8px;
        }
      }
    `}
  />
);

export default GlobalStyles;
