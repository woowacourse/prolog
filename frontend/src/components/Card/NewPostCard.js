import React, { forwardRef } from 'react';
import { Card, CARD_SIZE, CreatableSelectBox } from '..';
import { Editor } from '@toast-ui/react-editor';
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import { TitleInput, EditorWrapper } from './NewPostCard.styles';

const NewPostCard = forwardRef(({ postOrder, tagOptions }, ref) => {
  const assignRefValue = (key, value) =>
    (ref.current[postOrder] = { ...ref.current[postOrder], [key]: value });

  const selectTag = (newValue, actionMeta) => {
    if (actionMeta.action === 'create-option') {
      actionMeta.option.label = '#' + actionMeta.option.label;
    }
    assignRefValue('tags', newValue);
  };

  return (
    <Card size={CARD_SIZE.LARGE}>
      <TitleInput
        placeholder="제목을 입력해주세요"
        autoFocus
        ref={(element) => assignRefValue('title', element)}
      />
      <hr />
      <EditorWrapper>
        <Editor
          placeholder="여기에 학습로그를 작성해주세요."
          previewStyle="vertical"
          height="60vh"
          initialEditType="markdown"
          toolbarItems={['heading', 'bold', 'italic', 'strike']}
          ref={(element) => assignRefValue('content', element)}
        />
      </EditorWrapper>
      <CreatableSelectBox options={tagOptions} placeholder="#태그선택" onChange={selectTag} />
    </Card>
  );
});

export default NewPostCard;
