import styled from '@emotion/styled';
import { css } from '@emotion/react';
import COLOR from '../../constants/color';

// TODO: section 으로 바꾸기 -> aria-label 주기
const SelectBoxWrapper = styled.div`
  margin: 3rem 0;
`;

const Studylog = styled.div`
  margin-bottom: 4.8rem;

  .toastui-editor-contents h1,
  .toastui-editor-contents h2,
  .toastui-editor-contents h3,
  .toastui-editor-contents h4,
  .toastui-editor-contents h5,
  .toastui-editor-contents h6 {
    margin-top: 24px;
    margin-bottom: 16px;
    font-weight: 600;
    line-height: 1.25;
  }

  .toastui-editor-contents h1 {
    padding-bottom: 0.3em;
    font-size: 2em;
    border-bottom: 1px solid hsl(210deg 18% 87%);
  }

  .toastui-editor-contents h2 {
    padding-bottom: 0.3em;
    font-size: 1.5em;
    border-bottom: 1px solid hsl(210deg 18% 87%);
  }

  .toastui-editor-contents h3 {
    font-size: 1.25em;
  }

  .toastui-editor-contents ul > li::before {
    background-color: #222;
  }

  .toastui-editor-contents ol > li::before {
    color: #222;
  }
`;

const SubmitButtonStyle = css`
  width: 100%;
  background-color: ${COLOR.DARK_BLUE_800};
  color: ${COLOR.WHITE};
  font-weight: 500;
  margin: 0;

  &:hover {
    background-color: ${COLOR.DARK_BLUE_900};
  }
`;

export { SelectBoxWrapper, Studylog, SubmitButtonStyle };
