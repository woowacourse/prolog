/** @jsxImportSource @emotion/react */

import * as Styled from './Comment.style';
import { Link } from 'react-router-dom';

// 마크다운
import { Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';
import { CommentType } from '../../models/Comments';
import { ViewerWrapper } from '../../pages/StudylogPage/styles';
import { css } from '@emotion/react';

export interface CommentProps extends CommentType {}

const Comment = ({ author, content, createAt }: CommentProps) => {
  const { username, nickname, imageUrl } = author;

  return (
    <Styled.Root>
      <Styled.Top>
        <Styled.Left>
          <Link to={`/${username}`}>
            <Styled.Logo src={imageUrl} alt="프로필" />
            <span>{nickname}</span>
          </Link>
          <Styled.CreatedDate>{createAt}</Styled.CreatedDate>
        </Styled.Left>
        <Styled.Right>
          <button>수정</button>
          <button>삭제</button>
        </Styled.Right>
      </Styled.Top>
      <ViewerWrapper
        css={css`
          padding-left: 46px;
        `}
      >
        <Viewer
          initialValue={content}
          extendedAutolinks={true}
          plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
        />
      </ViewerWrapper>
    </Styled.Root>
  );
};

export default Comment;
