import React, { useState } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { SelectBox, Button, BUTTON_SIZE, NewPostCard } from '../../components';
import { nanoid } from 'nanoid';

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

const Post = styled.li`
  margin-bottom: 4.8rem;
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

const options = [
  '제가 로이드보다 젊습니다.',
  '학습로그 안쓰니?',
  '그 박재성 아닙니다.',
  '죽여줘 임동준.',
  '포초리 딱대.',
  '마이너스 5점.',
  '야!!!!넣어줘라~~~~~~~~~~~~~~~~~~',
];

const NewPostPage = () => {
  const [posts, setPosts] = useState([{ id: nanoid(), title: '', content: '' }]);

  const onFinishWriting = (e) => {
    e.preventDefault();
  };

  const setPost = (newPost) => {
    const targetPostIndex = posts.findIndex(({ id }) => newPost.id === id);

    setPosts([...posts.slice(0, targetPostIndex), newPost, ...posts.slice(targetPostIndex + 1)]);
  };

  return (
    <form onSubmit={onFinishWriting}>
      <SelectBoxWrapper>
        <SelectBox options={options} />
      </SelectBoxWrapper>
      <ul>
        {posts.map((post) => (
          <Post key={post.id}>
            <NewPostCard post={post} setPost={setPost} />
          </Post>
        ))}
      </ul>

      <Flex>
        <Button
          type="button"
          size={BUTTON_SIZE.LARGE}
          css={LogButtonStyle}
          onClick={() => setPosts([...posts, { id: nanoid(), title: '', content: '' }])}
        >
          로그추가
        </Button>
        <Button size={BUTTON_SIZE.LARGE} css={SubmitButtonStyle}>
          작성완료
        </Button>
      </Flex>
    </form>
  );
};

export default NewPostPage;
