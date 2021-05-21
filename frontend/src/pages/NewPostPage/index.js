import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { SelectBox, Button, BUTTON_SIZE, NewPostCard } from '../../components';
import { nanoid } from 'nanoid';
import { useDispatch, useSelector } from 'react-redux';
import { createPost } from '../../redux/actions/postAction';
import { PATH } from '../../constants';

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

/**
 * Represents a isScrolledIntoView.
 * @function isScrolledIntoView - 화면 scroll 영역을 벗어나는지 체크하는 함수
 * @param {element}  - current target which focus-in
 */
const isScrolledIntoView = (elem) => {
  const rect = elem.getBoundingClientRect();
  const elemTop = rect.top;
  const elemBottom = elem.nodeName === 'SPAN' ? rect.bottom - 370 : rect.bottom;

  const isVisible = elemTop >= 0 && elemBottom <= window.innerHeight;

  return isVisible;
};

const NewPostPage = () => {
  const [posts, setPosts] = useState([
    { id: nanoid(), title: '', content: '', tags: '#지하철 #노선도' },
  ]);
  const [category, setCategory] = useState(options[0]);
  const [currentElement, setCurrentElement] = useState(null);
  const history = useHistory();
  const accessToken = useSelector((state) => state.user.accessToken.data);
  const dispatch = useDispatch();

  useEffect(() => {
    if (!currentElement) return;

    /**
     * Represents a blurElem.
     * window OS 에서 Post에 한글을 입력할 때, focus가 원하는 영역으로 이동하지 않는 문제를 해결하기 위해 작성
     * @function blurElem - isScrolledIntoView()를 통해 visible 상태가 아니면 focus 를 해제하는 함수
     */
    const blurElem = () => {
      if (!isScrolledIntoView(currentElement)) {
        currentElement.blur();
      }
    };

    document.addEventListener('scroll', blurElem);

    return () => {
      document.removeEventListener('scroll', blurElem);
    };
  }, [currentElement]);

  // TODO : 작성 완료된 Posts를 서버로 보내는 함수 작성하기
  // TODO : category 등록하기
  const onFinishWriting = async (e) => {
    e.preventDefault();

    const postData = posts.map((post) => ({
      title: post.title,
      content: post.content,
      category,
      tags: post.tags.split('#').filter((v) => v),
    }));

    await dispatch(createPost(postData, accessToken));

    history.push('/');
  };

  const setPost = (newPost) => {
    const targetPostIndex = posts.findIndex(({ id }) => newPost.id === id);

    setPosts([
      ...posts.slice(0, targetPostIndex),
      { ...posts[targetPostIndex], ...newPost },
      ...posts.slice(targetPostIndex + 1),
    ]);
  };

  return (
    <form onSubmit={onFinishWriting}>
      <SelectBoxWrapper>
        <SelectBox options={options} selectedOption={category} setSelectedOption={setCategory} />
      </SelectBoxWrapper>
      <ul>
        {posts.map((post) => (
          <Post key={post.id}>
            <NewPostCard post={post} setPost={setPost} setCurrentElement={setCurrentElement} />
          </Post>
        ))}
      </ul>

      <Flex>
        <Button
          type="button"
          size={BUTTON_SIZE.LARGE}
          css={LogButtonStyle}
          onClick={() =>
            setPosts([...posts, { id: nanoid(), title: '', content: '', tags: '#지하철 #노선도' }])
          }
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
