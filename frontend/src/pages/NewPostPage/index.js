import React, { useState } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { SelectBox, Card, CARD_SIZE, Button, BUTTON_SIZE } from '../../components';

// TODO: section 으로 바꾸기 -> aria-label 주기
const SelectBoxWrapper = styled.div`
  margin: 3rem 0;
`;

const Flex = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 3rem 0;
`;

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

const LogButtonStyle = css`
  background-color: #e0e0e0;
  font-weight: 500;
`;

const SubmitButtonStyle = css`
  background-color: #153147;
  color: #ffffff;
  font-weight: 500;
`;

const NewPostPage = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [hashtag, setHashTag] = useState('');

  const options = [
    '제가 로이드보다 젊습니다.',
    '학습로그 안쓰니?',
    '그 박재성 아닙니다.',
    '죽여줘 임동준.',
    '포초리 딱대.',
    '마이너스 5점.',
  ];

  return (
    <>
      <SelectBoxWrapper>
        <SelectBox options={options} />
      </SelectBoxWrapper>

      <Card size={CARD_SIZE.LARGE}>
        <TitleInput
          placeholder="제목을 입력해주세요"
          onChange={({ target }) => setTitle(target.value)}
        />
        <hr />
        <div>
          <TextArea
            placeholder="마크업 형식으로 글을 작성해주세요"
            contentEditable
            onInput={({ target }) => setContent(target.innerText)}
          />
        </div>
        <TagInput placeholder="# 태그" />
      </Card>

      <Flex>
        <Button size={BUTTON_SIZE.LARGE} css={LogButtonStyle} onClick>
          로그추가
        </Button>
        <Button size={BUTTON_SIZE.LARGE} css={SubmitButtonStyle} onClick>
          작성완료
        </Button>
      </Flex>
    </>
  );
};

export default NewPostPage;
