import React, { forwardRef } from 'react';
import styled from '@emotion/styled';

import { Card, CARD_SIZE, CreatableSelectBox } from '..';
import { Editor } from '@toast-ui/react-editor';

import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';

import { POST_TITLE } from '../../constants';

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

const EditPostCard = forwardRef(({ post, tagOptions }, ref) => {
  const { title, tags, content } = post;
  const prevTags = tags?.map((tag) => ({
    value: tag.name,
    label: '#' + tag.name,
  }));

  const assignRefValue = (key, value) =>
    (ref.current = {
      ...ref.current,
      [key]: value,
    });

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
        required
        minLength={POST_TITLE.MIN_LENGTH}
        maxLength={POST_TITLE.MAX_LENGTH}
        autoFocus
        ref={(element) => assignRefValue('title', element)}
        defaultValue={title}
      />
      <hr />
      <Editor
        placeholder="여기에 학습로그를 작성해주세요."
        previewStyle="vertical"
        height="50vh"
        initialValue={content}
        initialEditType="markdown"
        toolbarItems={[['heading', 'bold', 'italic', 'strike']]}
        ref={(element) => assignRefValue('content', element)}
        extendedAutolinks={true}
        plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
      />
      <CreatableSelectBox
        defaultValue={prevTags}
        options={tagOptions}
        placeholder="#태그선택"
        onChange={selectTag}
      />
    </Card>
  );
});

export default EditPostCard;
