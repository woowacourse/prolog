import React, { useRef, useState } from 'react';
import { useHistory } from 'react-router-dom';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { SelectBox, Button, BUTTON_SIZE, NewPostCard } from '../../components';
import { nanoid } from 'nanoid';
import { useDispatch, useSelector } from 'react-redux';
import { createPost } from '../../redux/actions/postAction';

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
];

const tagsMockData = '#학습로그 #에디터 #힘들어';

const NewPostPage = () => {
  const [postIds, setPostIds] = useState([nanoid()]);
  const [category, setCategory] = useState(options[0]);
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const { error } = useSelector((state) => state.post.posts);
  const dispatch = useDispatch();

  const cardRefs = useRef([]);

  const onFinishWriting = async (e) => {
    e.preventDefault();

    const prologData = cardRefs.current.map(({ title, content, tags }) => ({
      category,
      title: title.value,
      content: content.getInstance().getMarkdown(),
      tags: tagsMockData.match(/#[가-힣a-z]+/g),
    }));

    await dispatch(createPost(prologData, accessToken));

    // TODO : fetch hook 통해서 에러처리 선언적으로 해주기
    if (error) {
      alert(error.message);

      return;
    }

    history.push('/');
  };

  return (
    <form onSubmit={onFinishWriting}>
      <SelectBoxWrapper>
        <SelectBox options={options} selectedOption={category} setSelectedOption={setCategory} />
      </SelectBoxWrapper>
      <ul>
        {postIds.map((postId, index) => (
          <Post key={postId}>
            <NewPostCard ref={cardRefs} postOrder={index} />
          </Post>
        ))}
      </ul>

      <Flex>
        <Button
          type="button"
          size={BUTTON_SIZE.LARGE}
          css={LogButtonStyle}
          onClick={() => setPostIds([...postIds, nanoid()])}
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
