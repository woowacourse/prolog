/** @jsxImportSource @emotion/react */

// Markdown Parser
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import { Editor } from '@toast-ui/react-editor';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';

import { markdownStyle } from '../../styles/markdown.styles';
import { FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import { EditorWrapperStyle } from '../../components/Introduction/Introduction.styles';
import { css } from '@emotion/react';
import { COLOR } from '../../constants';

const DEFAULT_EDITOR_HEIGHT = '480px';

const EditorStyle = css`
  .toastui-editor-defaultUI {
    border: 0;

    border: 1px solid ${COLOR.LIGHT_GRAY_500};
    border-radius: 0.5rem;
  }

  .toastui-editor-toolbar {
    background-color: ${COLOR.LIGHT_BLUE_200};
    border-radius: 0.5rem;
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

const ReportDescEditor = ({ initialContent, editorRef, editorHeight = DEFAULT_EDITOR_HEIGHT }) => {
  return (
    <section css={[FlexStyle, FlexColumnStyle, EditorWrapperStyle, EditorStyle, markdownStyle]}>
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
    </section>
  );
};

export default ReportDescEditor;
