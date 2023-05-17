import { css } from '@emotion/react';

export const ResetScrollBar = css`
  ::-webkit-scrollbar {
    width: auto;
  }

  ::-webkit-scrollbar-track {
    -webkit-box-shadow: auto;
    -webkit-border-radius: auto;
    border-radius: auto;
    background: auto;
  }

  ::-webkit-scrollbar-thumb {
    -webkit-border-radius: auto;
    border-radius: auto;
    background: auto;
    -webkit-box-shadow: auto;
  }

  ::-webkit-scrollbar-thumb:window-inactive {
    background: auto;
  }
`;
