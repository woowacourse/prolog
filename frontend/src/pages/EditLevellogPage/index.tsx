/** @jsxImportSource @emotion/react */

import styled from '@emotion/styled';
import Editor from '../../components/Editor/Editor';
import NewLevellogQnAList from '../../components/Lists/NewLevellogQnAInputList';
import { COLOR } from '../../constants';
import useEditLevellog from '../../hooks/Levellog/useEditLevellog';
import { MainContentStyle } from '../../PageRouter';

const EditLevellogPage = () => {
  const {
    NewLevellogQnAListProps,
    title,
    onChangeTitle,
    content,
    editorContentRef,
    isLoading,
    onEditLevellog,
    id,
  } = useEditLevellog();

  return (
    <div css={MainContentStyle}>
      {!isLoading && (
        <>
          <Editor
            key={id}
            title={title}
            content={content}
            onChangeTitle={onChangeTitle}
            height="40vh"
            editorContentRef={editorContentRef}
          />
          <NewLevellogQnAList {...NewLevellogQnAListProps} />
          <SubmitButton type="submit" onClick={onEditLevellog}>
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
