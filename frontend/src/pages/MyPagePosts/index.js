import React, { useState } from 'react';
import { css } from '@emotion/react';
import { useHistory } from 'react-router-dom';
import { PATH } from '../../constants';
import { Button, BUTTON_SIZE } from '../../components';
import useFetch from '../../hooks/useFetch';
import { requestGetPosts } from '../../service/requests';
import { Content, Description, Mission, Title, Tags, PostItem, ButtonList } from './styles';

const EditButtonStyle = css`
  border: 1px solid #e6e6e6;
  background-color: #fff;

  &:hover {
    background-color: #e8e8e8;
  }
`;

const DeleteButtonStyle = css`
  border: 1px solid #e6e6e6;
  background-color: #f59898;

  &:hover {
    background-color: #f08484;
  }
`;
const MyPagePosts = () => {
  const history = useHistory;

  const [hoverdPostId, setHoveredPostId] = useState(0);

  const [postList] = useFetch([], requestGetPosts);

  const goTargetPost = (id) => (event) => {
    if (event?.target !== event?.currentTarget) return;

    history.push(`${PATH.POST}/${id}`);
  };

  const goEditTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}/edit`);
  };

  const deleteTargetPost = (id) => {
    // delete
  };

  return (
    <>
      {postList?.map((post) => {
        const { id, mission, title, tags } = post;

        return (
          <PostItem
            key={id}
            size="SMALL"
            onClick={goTargetPost(id)}
            onMouseEnter={() => setHoveredPostId(id)}
            onMouseLeave={() => setHoveredPostId(0)}
          >
            <Content>
              <Description>
                <Mission>{mission.name}</Mission>
                <Title>{title}</Title>
                <Tags>
                  {tags.map(({ id, name }) => (
                    <span key={id}>{`#${name} `}</span>
                  ))}
                </Tags>
              </Description>
            </Content>
            {hoverdPostId === id && (
              <ButtonList>
                <Button
                  size={BUTTON_SIZE.X_SMALL}
                  type="button"
                  css={EditButtonStyle}
                  alt="수정 버튼"
                  onClick={() => goEditTargetPost(id)}
                >
                  수정
                </Button>
                <Button
                  size={BUTTON_SIZE.X_SMALL}
                  type="button"
                  css={DeleteButtonStyle}
                  alt="삭제 버튼"
                  onClick={() => deleteTargetPost(id)}
                >
                  삭제
                </Button>
              </ButtonList>
            )}
          </PostItem>
        );
      })}
    </>
  );
};

export default MyPagePosts;
