/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { ChangeEventHandler, FormEventHandler, MouseEventHandler, MutableRefObject } from 'react';
import { COLOR } from '../../constants';
import { Tag } from '../../models/Studylogs';
import { getFlexStyle } from '../../styles/flex.styles';
import Editor from './Editor';
import Sidebar from './Sidebar';

const SubmitButtonStyle = css`
  width: 100%;
  background-color: ${COLOR.LIGHT_BLUE_300};
  padding: 1rem 0;
  border-radius: 1.6rem;

  :hover {
    background-color: ${COLOR.LIGHT_BLUE_500};
  }
`;

const TempSaveButtonStyle = css`
  width: 100%;
  background-color: ${COLOR.LIGHT_GRAY_400};
  padding: 1rem 0;
  border-radius: 1.6rem;

  :hover {
    background-color: ${COLOR.LIGHT_GRAY_600};
  }
`;

type SelectOption = { value: string; label: string };

interface StudylogEditorProps {
  title: string;
  contentRef: MutableRefObject<unknown>;
  selectedMissionId?: number | null;
  selectedSessionId?: number | null;
  selectedTags?: Tag[];
  content?: string | null;

  onChangeTitle: ChangeEventHandler<HTMLInputElement>;
  onSelectMission: (mission: SelectOption) => void;
  onSelectSession: (session: SelectOption) => void;
  onSelectTag: (tags: Tag[], actionMeta: { option: { label: string } }) => void;
  onSubmit?: FormEventHandler<HTMLFormElement>;
  onTempSave?: MouseEventHandler<HTMLButtonElement>;
}

const StudylogEditor = ({
  title,
  selectedMissionId = null,
  selectedSessionId = null,
  selectedTags = [],
  contentRef,
  content,
  onChangeTitle,
  onSelectMission,
  onSelectSession,
  onSelectTag,
  onSubmit,
  onTempSave,
}: StudylogEditorProps): JSX.Element => {
  return (
    <form onSubmit={onSubmit}>
      <div css={[getFlexStyle({ columnGap: '1rem' })]}>
        <div css={[getFlexStyle({ flexGrow: 1, flexDirection: 'column' })]}>
          <Editor
            height="80rem"
            title={title}
            editorContentRef={contentRef}
            onChangeTitle={onChangeTitle}
            content={content}
          />
          <div
            css={[
              getFlexStyle({ justifyContent: 'flex-end', columnGap: '2rem' }),
              css`
                margin-top: 1rem;
              `,
            ]}
          >
            {onTempSave && (
              <button type="button" css={[TempSaveButtonStyle]} onClick={onTempSave}>
                임시 저장
              </button>
            )}
            <button type="submit" css={[SubmitButtonStyle]}>
              작성 완료
            </button>
          </div>
        </div>
        <Sidebar
          selectedMissionId={selectedMissionId}
          selectedSessionId={selectedSessionId}
          selectedTagList={selectedTags}
          onSelectTag={onSelectTag}
          onSelectMission={onSelectMission}
          onSelectSession={onSelectSession}
        />
      </div>
    </form>
  );
};

export default StudylogEditor;
