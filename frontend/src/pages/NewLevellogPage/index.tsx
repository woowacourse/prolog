/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';

import { MainContentStyle } from '../../PageRouter';

import Editor from '../../components/Editor/Editor';
import useNewLevellog from '../../hooks/Levellog/useNewLevellog';
import { FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import LevellogQnAList from '../../components/Lists/LevellogQnAList';
import styled from '@emotion/styled';
import { COLOR } from '../../constants';

const NewLevellogPage = () => {
  const {
    createNewLevellog,
    editorContentRef,
    onChangeTitle,
    title,
    QnAListProps,
  } = useNewLevellog();

  return (
    <div
      css={[
        MainContentStyle,
        FlexStyle,
        FlexColumnStyle,
        css`
          gap: 30px;
        `,
      ]}
    >
      <Editor
        title={title}
        onChangeTitle={onChangeTitle}
        height="40vh"
        editorContentRef={editorContentRef}
      />
      <LevellogQnAList QnAListProps={QnAListProps} />
      <SubmitButton type="submit" onClick={createNewLevellog}>
        제출하기
      </SubmitButton>
    </div>
  );
};

export default NewLevellogPage;

const SubmitButton = styled.button`
  background-color: ${COLOR.LIGHT_BLUE_400};
  width: 100%;
  border-radius: 4px;
  padding: 1rem;
`;
