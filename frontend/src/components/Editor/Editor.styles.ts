import { css } from '@emotion/react';
import { COLOR } from '../../constants';

export const EditorWrapperStyle = css`
  .toastui-editor-mode-switch {
    height: 4.8rem;
    border-bottom-right-radius: 2rem;
    border-bottom-left-radius: 2rem;
  }
`;

export const EditorTitleStyle = css`
  padding-bottom: 2rem;
  margin-bottom: 2rem;
  border-bottom: 1px solid ${COLOR.LIGHT_GRAY_100};

  > input {
    width: 100%;
    font-size: 2.4rem;
    border: none;
    //padding: 0.4rem 1rem;
    outline: none;
  }
`;
