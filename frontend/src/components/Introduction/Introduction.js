/** @jsxImportSource @emotion/react */

import { useContext, useState } from 'react';
import { css } from '@emotion/react';
import { useParams } from 'react-router-dom';

import useMutation from '../../hooks/useMutation';
import useRequest from '../../hooks/useRequest';
import {
  requestEditProfileIntroduction,
  requestGetProfileIntroduction,
} from '../../service/requests';
import EditIntroduction from './EditIntroduction';
import { Button } from '..';
import { ERROR_MESSAGE } from '../../constants';

// Markdown Parser
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import { Viewer } from '@toast-ui/react-editor';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';

import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentCenterStyle,
} from '../../styles/flex.styles';
import { markdownStyle } from '../../styles/markdown.styles';
import { EditButtonStyle, WrapperStyle } from './Introduction.styles';
import { UserContext } from '../../contexts/UserProvider';

const Introduction = () => {
  const { username } = useParams();
  const [isEditing, setIsEditing] = useState(false);
  const [editorContentRef, setEditorContentRef] = useState('');

  const { user } = useContext(UserContext);
  const { accessToken, loginName } = user;

  const { response, fetchData } = useRequest({ text: '' }, () =>
    requestGetProfileIntroduction(username)
  );
  const data = response?.text ?? '';

  const isOwner = username === loginName;
  const hasIntro = !!data.length;

  const { mutate: editProfileIntro } = useMutation(
    () =>
      requestEditProfileIntroduction(
        username,
        { text: editorContentRef.getInstance().getMarkdown() },
        accessToken
      ),
    {
      onSuccess: async () => {
        await fetchData();

        setIsEditing(false);
      },
      onError: (error) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  if (!isOwner && !hasIntro) {
    return <></>;
  }

  return (
    <div
      css={[
        WrapperStyle,
        FlexStyle,
        JustifyContentCenterStyle,
        AlignItemsCenterStyle,
        isOwner &&
          !isEditing &&
          css`
            button {
              opacity: 0;
            }

            :hover {
              button {
                opacity: 1;
              }
            }
          `,
      ]}
    >
      {isOwner && !isEditing && (
        <Button
          size="X_SMALL"
          type="button"
          cssProps={EditButtonStyle}
          onClick={() => setIsEditing(true)}
        >
          수정
        </Button>
      )}

      {isOwner && !hasIntro && !isEditing && (
        <p
          css={[
            css`
              margin: 0;
              width: 100%;
            `,
            FlexStyle,
            JustifyContentCenterStyle,
            AlignItemsCenterStyle,
          ]}
        >
          소개글을 작성해 보세요! :D
        </p>
      )}

      {!isEditing && hasIntro && (
        <section
          css={[
            markdownStyle,
            css`
              width: 100%;
              padding: 1.8rem 2rem 1.8rem;
            `,
          ]}
        >
          <Viewer
            initialValue={data}
            extendedAutolinks={true}
            plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
          />
        </section>
      )}

      {isEditing && (
        <EditIntroduction
          initialContent={data}
          onEdit={editProfileIntro}
          editorRef={setEditorContentRef}
          onCancel={() => setIsEditing(false)}
        />
      )}
    </div>
  );
};

export default Introduction;
