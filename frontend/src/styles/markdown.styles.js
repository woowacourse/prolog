import { css } from '@emotion/react';
import { COLOR } from '../constants';

export const markdownStyle = css`
  .toastui-editor-md-preview,
  .toastui-editor-contents {
    && * {
      margin: 0;
      line-height: 2;
    }

    && h1:first-of-type {
      margin-top: 0;
    }

    h1 {
      border-bottom: 2px solid ${COLOR.LIGHT_GRAY_300};
      padding: 0 0 7px;
    }

    h3 {
      font-size: 1.8rem;
    }

    a {
      text-decoration: none;
      font-size: 1.6rem;
    }

    p {
      font-size: 1.4rem;
    }

    && ul li::before {
      margin-top: 8px;
      transform: translateY(70%);
    }

    && ol li::before {
      font-size: 1.4rem;
      margin-top: 3px;
    }

    && hr {
      margin: 2rem 0;
    }

    && blockquote {
      margin: 2rem 0;
    }
  }
`;
