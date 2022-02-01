import { css } from '@emotion/react';
import { COLOR } from '../../constants';

export const WrapperStyle = css`
  width: 100%;
  min-height: 12rem;

  margin-bottom: 2rem;

  position: relative;

  background-color: ${COLOR.WHITE};
  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  border-radius: 2rem;
`;

export const EditButtonStyle = css`
  width: 6rem;
  height: 3rem;
  padding: 0.2rem 0.8rem;

  position: absolute;
  top: 1.6rem;
  right: 2rem;

  background-color: ${COLOR.LIGHT_GRAY_100};

  border: 1px solid ${COLOR.LIGHT_GRAY_200};
  border-radius: 2rem;

  font-size: 1.4rem;
  color: ${COLOR.BLACK_800};

  :hover {
    background-color: ${COLOR.LIGHT_GRAY_300};
  }
`;

export const EditorWrapperStyle = css`
  width: 100%;
  border-radius: inherit;
  padding-bottom: 1rem;

  > h2 {
    padding: 1rem 2rem 0;

    font-size: 2rem;

    background-color: ${COLOR.LIGHT_BLUE_200};
    border-radius: inherit;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
  }
`;

export const EditorStyle = css`
  .toastui-editor-defaultUI {
    border: 0;
  }

  .toastui-editor-toolbar {
    background-color: ${COLOR.LIGHT_BLUE_200};
  }

  .toastui-editor-md-tab-container,
  .toastui-editor-defaultUI-toolbar {
    background-color: transparent;
  }

  .toastui-editor-defaultUI-toolbar button {
    background-color: ${COLOR.WHITE};

    :hover {
      background-color: ${COLOR.LIGHT_GRAY_100};
    }
  }
`;

export const SaveButtonStyle = css`
  margin-left: 1rem;

  background-color: ${COLOR.LIGHT_BLUE_200};
  border-radius: 1.2rem;

  color: ${COLOR.BLACK_800};

  :hover {
    background-color: ${COLOR.LIGHT_BLUE_400};
  }
`;

export const CancelButtonStyle = css`
  ${SaveButtonStyle};

  background-color: ${COLOR.LIGHT_GRAY_200};

  :hover {
    background-color: ${COLOR.LIGHT_GRAY_400};
  }
`;
