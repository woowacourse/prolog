import { css } from '@emotion/react';
import { COLOR } from '../../constants';

export const EditorWrapperStyle = css`
  border-radius: 2rem;
  border: 1px solid ${COLOR.LIGHT_GRAY_100};

  .toastui-editor-mode-switch {
    height: 4.8rem;
    border-bottom-right-radius: 2rem;
    border-bottom-left-radius: 2rem;
  }
`;

export const EditorTitleStyle = css`
  padding: 2rem 1.6rem 1.6rem;
  background-color: ${COLOR.LIGHT_BLUE_200};
  border-top-right-radius: 2rem;
  border-top-left-radius: 2rem;

  > input {
    width: 100%;
    font-size: 2.4rem;
    border-radius: 1rem;
    border: none;
    padding: 0.4rem 1rem;
  }
`;
