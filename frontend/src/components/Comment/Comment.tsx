/** @jsxImportSource @emotion/react */

import * as Styled from './Comment.style';
import { Link } from 'react-router-dom';

// 마크다운
import { Editor as ToastEditor, Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';
import { CommentRequest, CommentType } from '../../models/Comment';
import { ViewerWrapper } from '../../pages/StudylogPage/styles';
import { css } from '@emotion/react';
import Editor from '../Editor/Editor';
import { useRef, useState } from 'react';
import { COLOR } from '../../enumerations/color';
import { EditorForm, SubmitButton } from './CommentList.style';

export interface CommentProps extends CommentType {
  editComment: (commentId: number, body: CommentRequest) => void;
  deleteComment: (commentId: number) => void;
}

const Comment = ({ id, member, content, createAt, editComment, deleteComment }: CommentProps) => {
  const { username, nickname, imageUrl } = member;

  const [isEditMode, setIsEditMode] = useState(false);
  const editorContentRef = useRef<ToastEditor>(null);

  const onSubmitEditedComment = () => {
    if (window.confirm('댓글을 수정하시겠아요?')) {
      const content = editorContentRef.current?.getInstance().getMarkdown() || '';

      editComment(id, { content });
      setIsEditMode(false);
    }
  };

  const onClickEditButton = () => {
    setIsEditMode(true);
  };

  const onClickDeleteButton = () => {
    if (window.confirm('댓글을 삭제하시겠아요?')) {
      deleteComment(id);
    }
  };

  return (
    <>
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
            <button onClick={onClickEditButton}>수정</button>
            <button onClick={onClickDeleteButton}>삭제</button>
          </Styled.Right>
        </Styled.Top>
        <ViewerWrapper
          css={css`
            padding-left: 46px;
          `}
        >
          <Viewer
            // initialValue가 바뀌어도 화면에 반영되지 않는다. key를 바꿔주어 렌더링 시킨다.
            key={content}
            initialValue={content}
            extendedAutolinks={true}
            plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
          />
        </ViewerWrapper>
      </Styled.Root>
      {isEditMode && (
        <EditorForm
          css={css`
            margin-bottom: 30px;
          `}
        >
          <Editor
            height="25rem"
            hasTitle={false}
            editorContentRef={editorContentRef}
            content={content}
          />
          <Styled.ButtonContainer>
            <SubmitButton
              css={css`
                background-color: ${COLOR.RED_300};
                :hover {
                  background-color: ${COLOR.RED_500};
                }
              `}
              onClick={() => setIsEditMode(false)}
            >
              취소
            </SubmitButton>
            <SubmitButton onClick={onSubmitEditedComment}>수정</SubmitButton>
          </Styled.ButtonContainer>
        </EditorForm>
      )}
    </>
  );
};

export default Comment;
