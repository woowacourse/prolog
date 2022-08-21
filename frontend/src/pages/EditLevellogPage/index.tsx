/** @jsxImportSource @emotion/react */

import styled from '@emotion/styled';
import Editor from '../../components/Editor/Editor';
import EditLevellogQnAList from '../../components/Lists/NewLevellogQnAInputList';
import { COLOR } from '../../constants';
import useEditLevellog from '../../hooks/Levellog/useEditLevellog';
import { MainContentStyle } from '../../PageRouter';

const EditLevellogPage = () => {
  const {
    title,
    onChangeTitle,
    content,
    editorContentRef,
    isLoading,
    editLevellog,
    EditLevellogQnAListProps,
  } = useEditLevellog();

  return (
    <div css={MainContentStyle}>
      {!isLoading && (
        <>
          <Editor
            title={title}
            content={content}
            onChangeTitle={onChangeTitle}
            height="40vh"
            editorContentRef={editorContentRef}
          />
          <EditLevellogQnAList {...EditLevellogQnAListProps} />
          <SubmitButton type="submit" onClick={editLevellog}>
            수정하기
          </SubmitButton>
        </>
      )}
    </div>
  );
};

export default EditLevellogPage;

const SubmitButton = styled.button`
  background-color: ${COLOR.LIGHT_BLUE_400};
  width: 100%;
  border-radius: 4px;
  padding: 1rem;
`;
