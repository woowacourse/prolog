/** @jsxImportSource @emotion/react */

import { Button } from '..';
import { FlexColumnStyle, FlexStyle, JustifyContentEndStyle } from '../../styles/flex.styles';
import {
  CancelButtonStyle,
  EditorStyle,
  EditorWrapperStyle,
  SaveButtonStyle,
} from './Introduction.styles';

// Markdown Parser
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import { Editor } from '@toast-ui/react-editor';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';
import { css } from '@emotion/react';

const DEFAULT_EDITOR_HEIGHT = '480px';

const EditIntroduction = ({
  initialContent,
  editorRef,
  onEdit,
  editorHeight = DEFAULT_EDITOR_HEIGHT,
  onCancel,
}) => {
  return (
    <section css={[FlexStyle, FlexColumnStyle, EditorWrapperStyle, EditorStyle]}>
      <h2>자기소개 수정</h2>
      <Editor
        ref={editorRef}
        initialValue={initialContent}
        height={editorHeight}
        initialEditType="markdown"
        toolbarItems={[
          ['heading', 'bold', 'italic', 'strike'],
          ['hr', 'quote'],
          ['ul', 'ol', 'task'],
          ['indent'],
        ]}
        extendedAutolinks={true}
        plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
      />
      <div
        css={[
          FlexStyle,
          JustifyContentEndStyle,
          css`
            padding: 1rem 1rem 0;
          `,
        ]}
      >
        <Button size="X_SMALL" cssProps={CancelButtonStyle} onClick={onCancel}>
          취소
        </Button>
        <Button size="X_SMALL" cssProps={SaveButtonStyle} onClick={onEdit}>
          저장
        </Button>
      </div>
    </section>
  );
};

export default EditIntroduction;
