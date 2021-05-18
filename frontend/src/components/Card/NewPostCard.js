import React, { useState } from 'react';
import styled from '@emotion/styled';
import { Card, CARD_SIZE } from '..';

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

const TextArea = styled.span`
  display: inline-block;
  width: 100%;
  padding: 1rem 0;

  font-size: 1.6rem;
  line-height: 2.4rem;
  min-height: 41rem;
  font-weight: 400;

  outline: none;

  &:empty:before {
    content: attr(placeholder);
    display: inline-block;
    color: grey;
    cursor: text;
  }
`;

const TagInput = styled.input`
  width: 100%;
  height: 2.4rem;
  margin: 1rem 0;

  font-size: 2rem;
  line-height: 3rem;
  font-weight: 400;

  border: none;
  outline: none;

  &::placeholder {
    font-weight: 300;
  }
`;

const NewPostCard = ({ post, setPost }) => {
  const { id, title, content } = post;
  const [card, setCard] = useState({ id, title, content });

  const onChangeTitle = ({ target }) => {
    setCard((prevState) => ({
      ...prevState,
      title: target.value,
    }));
  };

  return (
    <Card size={CARD_SIZE.LARGE}>
      <TitleInput
        placeholder="제목을 입력해주세요"
        value={card.title}
        onChange={onChangeTitle}
        onBlur={() => setPost(card, id)}
      />
      <hr />
      <TextArea
        placeholder="마크업 형식으로 글을 작성해주세요"
        contentEditable
        suppressContentEditableWarning
        tabIndex="0"
        onBlur={({ target }) => setPost({ ...card, content: target.textContent }, id)}
      >
        {card.content}
      </TextArea>
      <TagInput placeholder="# 태그" />
    </Card>
  );
};

export default NewPostCard;
