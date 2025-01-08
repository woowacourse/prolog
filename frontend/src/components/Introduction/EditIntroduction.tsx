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
import { markdownStyle } from '../../styles/markdown.styles';

const DEFAULT_EDITOR_HEIGHT = '480px';

interface EditIntroductionProps {
  initialContent?: string;
  editorRef: any;
  onEdit: () => void;
  editorHeight?: string;
  onCancel: () => void;
}

const EditIntroduction = ({
  initialContent,
  editorRef,
  onEdit,
  editorHeight = DEFAULT_EDITOR_HEIGHT,
  onCancel,
}: EditIntroductionProps) => {
  return (
    <section css={[FlexStyle, FlexColumnStyle, EditorWrapperStyle, EditorStyle, markdownStyle]}>
      <Editor
        ref={editorRef}
        initialValue={initialContent}
        height={editorHeight}
        hideModeSwitch={true}
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
            padding: 4rem 2rem;
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
