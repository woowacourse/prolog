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
import { EditorForm, SubmitButton, ViewerWrapper } from '../../pages/StudylogPage/styles';
import { css } from '@emotion/react';
import Editor from '../Editor/Editor';
import { useContext, useRef, useState } from 'react';
import { COLOR } from '../../enumerations/color';
import { UserContext } from '../../contexts/UserProvider';
import {ActionButton, DeleteButton} from './Comment.style';

export interface CommentProps extends CommentType {
  editComment: (commentId: number, body: CommentRequest) => void;
  deleteComment: (commentId: number) => void;
}

const Comment = ({ id, author, content, createAt, editComment, deleteComment }: CommentProps) => {
  const { user } = useContext(UserContext);
  const { username, nickname, imageUrl } = author;

  const [isEditMode, setIsEditMode] = useState(false);
  const editorContentRef = useRef<ToastEditor>(null);

  const onSubmitEditedComment = () => {
    const contentOnEdit = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (content === contentOnEdit) {
      setIsEditMode(false);

      return;
    }

    if (window.confirm('댓글을 수정하시겠아요?')) {
      const content = editorContentRef.current?.getInstance().getMarkdown() || '';

      editComment(id, { content });
      setIsEditMode(false);
    }
  };

  const onClickEditButton = () => {
    setIsEditMode(true);
  };

  const onClickCancelButton = () => {
    const contentOnEdit = editorContentRef.current?.getInstance().getMarkdown() || '';

    if (content === contentOnEdit) {
      setIsEditMode(false);

      return;
    }

    if (window.confirm('수정 중인 댓글이 사라집니다. 댓글 수정을 취소하시겠어요?')) {
      setIsEditMode(false);
    }
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
            </Link>
            <div
              css={css`
                display: flex;
                flex-direction: column;
              `}
            >
              <Link to={`/${username}`}>
                <Styled.MemberName>{nickname}</Styled.MemberName>
              </Link>
              <Styled.CreatedDate>{new Date(createAt).toLocaleString('ko-KR')}</Styled.CreatedDate>
            </div>
          </Styled.Left>
          {user.userId === author.id && (
            <Styled.Right>
              <ActionButton type="button" onClick={onClickEditButton}>
                수정
              </ActionButton>
              <DeleteButton type="button" onClick={onClickDeleteButton}>
                삭제
              </DeleteButton>
            </Styled.Right>
          )}
        </Styled.Top>
        <ViewerWrapper
          css={css`
            padding-left: 60px;
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
              type="button"
              css={css`
                background-color: ${COLOR.RED_300};
                :hover {
                  background-color: ${COLOR.RED_500};
                }
              `}
              onClick={onClickCancelButton}
            >
              취소
            </SubmitButton>
            <SubmitButton type="button" onClick={onSubmitEditedComment}>
              수정
            </SubmitButton>
          </Styled.ButtonContainer>
        </EditorForm>
      )}
    </>
  );
};

export default Comment;
