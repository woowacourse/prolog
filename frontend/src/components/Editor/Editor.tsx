/** @jsxImportSource @emotion/react */

// Markdown Parser
import { Editor as ToastEditor } from '@toast-ui/react-editor';

import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';
import { markdownStyle } from '../../styles/markdown.styles';
import { EditorStyle } from '../Introduction/Introduction.styles';
import { EditorWrapperStyle } from './Editor.styles';
import { ChangeEventHandler, MutableRefObject } from 'react';
import { getSize } from '../../utils/styles';
import useImage from '../../hooks/useImage';

interface EditorProps {
  height: number | string;
  title?: string;
  hasTitle?: boolean;
  titlePlaceholder?: string;
  titleReadOnly?: boolean;
  editorContentRef: MutableRefObject<unknown>;
  content?: string;
  onChangeTitle?: ChangeEventHandler<HTMLInputElement>;
  onChangeContent?: () => void;
  toolbarItems?: string[][];
}

const DEFAULT_TOOLBAR_ITEMS = [
  ['heading', 'bold', 'italic', 'strike'],
  ['hr', 'quote'],
  ['ul', 'ol', 'task'],
  ['indent'],
];

const Editor = (props: EditorProps): JSX.Element => {
  const { height, content, editorContentRef, toolbarItems = DEFAULT_TOOLBAR_ITEMS } = props;

  const { uploadImage } = useImage();

  return (
    <div css={[EditorStyle, markdownStyle, EditorWrapperStyle]}>
      <ToastEditor
        ref={(element) => {
          editorContentRef.current = element;
        }}
        initialValue={content}
        height={getSize(height)}
        initialEditType="markdown"
        hideModeSwitch={true}
        toolbarItems={toolbarItems}
        extendedAutolinks={true}
        previewStyle={'tab'}
        plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
        hooks={{
          addImageBlobHook: uploadImage,
        }}
      />
    </div>
  );
};

export default Editor;
