import React, { FormEventHandler, RefObject, useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';
import Editor from '../Editor/Editor';
import { Editor as ToastEditor } from '@toast-ui/react-editor';
import * as Styled from './CommentEditorForm.style';

interface CommentEditorFormProps {
  onSubmit: FormEventHandler;
  editorContentRef: RefObject<ToastEditor>;
}

const CommentEditorForm = ({ onSubmit, editorContentRef }: CommentEditorFormProps) => {
  const { user } = useContext(UserContext);
  const { isLoggedIn } = user;

  return (
    <Styled.EditorForm onSubmit={onSubmit}>
      <Editor height="25rem" hasTitle={false} editorContentRef={editorContentRef} />
      <Styled.SubmitButton disabled={!isLoggedIn}>
        {isLoggedIn ? '작성 완료' : '로그인 후 작성해주세요.'}
      </Styled.SubmitButton>
    </Styled.EditorForm>
  );
};

export default CommentEditorForm;
