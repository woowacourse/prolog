import React, { forwardRef } from 'react';
import styled from '@emotion/styled';

import { Card, CARD_SIZE, CreatableSelectBox } from '..';
import { Editor } from '@toast-ui/react-editor';

import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

const TitleInput = styled.input`
  width: 100%;
  height: 5.4rem;
  padding: 0;
  margin: 1rem 0;

  font-size: 4.4rem;
  line-height: 6.6rem;
  font-weight: 700;

  border: none;
  outline: none;

  &::placeholder {
    font-weight: 500;
  }
`;

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
      <Editor
        placeholder="여기에 학습로그를 작성해주세요."
        previewStyle="vertical"
        height="50vh"
        initialEditType="markdown"
        toolbarItems={['heading', 'bold', 'italic', 'strike']}
        ref={(element) => assignRefValue('content', element)}
      />
      <CreatableSelectBox options={tagOptions} placeholder="#태그선택" onChange={selectTag} />
    </Card>
  );
});

export default NewPostCard;
