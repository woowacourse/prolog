/** @jsxImportSource @emotion/react */

import { useState } from 'react';
import { css } from '@emotion/react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';

import useMutation from '../../hooks/useMutation';
import useRequest from '../../hooks/useRequest';
import {
  requestEditProfileIntroduction,
  requestGetProfileIntroduction,
} from '../../service/requests';
import { COLOR, ERROR_MESSAGE } from '../../constants';

import { Button } from '..';

// Markdown Parser
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import { Editor, Viewer } from '@toast-ui/react-editor';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';

import {
  AlignItemsCenterStyle,
  FlexColumnStyle,
  FlexStyle,
  JustifyContentCenterStyle,
} from '../../styles/flex.styles';
import { markdownStyle } from '../../styles/markdown.styles';

const Introduction = () => {
  const { username } = useParams();
  const [isEditing, setIsEditing] = useState(false);

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const loginName = useSelector((state) => state.user.profile.data?.username);

  const isOwner = username === loginName;

  const { response, fetchData } = useRequest({ text: '' }, () =>
    requestGetProfileIntroduction(username)
  );
  const data = response?.text ?? '';

  const [content, setContent] = useState('');

  const { mutate: editProfileIntro } = useMutation(
    () => {
      const data = content.getInstance().getMarkdown();

      return requestEditProfileIntroduction(username, { text: data }, accessToken);
    },
    async () => {
      await fetchData();

      setIsEditing(false);
    },
    (error) => {
      alert(ERROR_MESSAGE[error.code]);
    }
  );

  return (
    <>
      <div
        css={[
          !isOwner && data.trim(' ').length === 0
            ? css`
                display: none;
              `
            : css`
                width: 100%;
                min-height: 12rem;
                max-height: fit-content;

                margin-bottom: 2rem;

                position: relative;

                background-color: ${COLOR.WHITE};
                border: 1px solid ${COLOR.LIGHT_GRAY_200};
                border-radius: 2rem;

                justify-content: center;
                align-items: center;
              `,
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
          FlexStyle,
        ]}
      >
        {isOwner && !isEditing && data.trim(' ').length === 0 && (
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
        {isOwner && !isEditing && (
          <button
            type="button"
            css={css`
              padding: 0.2rem 0.8rem;

              position: absolute;
              top: 1.6rem;
              right: 2rem;

              background-color: ${COLOR.LIGHT_GRAY_100};

              border: 1px solid ${COLOR.LIGHT_GRAY_200};
              border-radius: 2rem;

              font-size: 1.4rem;

              :hover {
                background-color: ${COLOR.LIGHT_GRAY_300};
              }
            `}
            onClick={() => setIsEditing(true)}
          >
            수정
          </button>
        )}
        {!isEditing && data.trim(' ').length !== 0 && (
          <section
            css={[
              markdownStyle,
              css`
                width: 100%;
                padding: 0rem 2rem 1.8rem;
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
          <section
            css={[
              FlexStyle,
              FlexColumnStyle,
              css`
                width: 100%;
                border-radius: inherit;
                padding-bottom: 1rem;

                h2 {
                  padding: 1rem 2rem 0;

                  font-size: 2rem;

                  background-color: ${COLOR.LIGHT_BLUE_200};
                  border-radius: inherit;
                  border-bottom-left-radius: 0;
                  border-bottom-right-radius: 0;
                }

                .toastui-editor-defaultUI {
                  border: 0;
                }

                .toastui-editor-toolbar {
                  background-color: ${COLOR.LIGHT_BLUE_200};
                  /* background-color: transparent; */
                }

                .toastui-editor-md-tab-container,
                .toastui-editor-defaultUI-toolbar {
                  background-color: transparent;
                }

                .toastui-editor-defaultUI-toolbar button {
                  background-color: ${COLOR.WHITE};

                  :hover {
                    background-color: ${COLOR.LIGHT_GRAY_100};
                  }
                }

                .toastui-editor-md-preview {
                  ${markdownStyle}
                }
              `,
            ]}
          >
            <h2>자기소개 수정</h2>
            <Editor
              ref={setContent}
              initialValue={data}
              height="480px"
              initialEditType="markdown"
              toolbarItems={[
                ['heading', 'bold', 'italic', 'strike'],
                ['hr', 'quote'],
                ['ul', 'ol', 'task'],
                ['indent'],
              ]}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
            <Button
              size="X_SMALL"
              type="button"
              cssProps={css`
                margin-top: 1rem;
                background-color: ${COLOR.LIGHT_BLUE_200};
                color: ${COLOR.BLACK_800};
                border-radius: 1.2rem;
                align-self: flex-end;
                margin-right: 1rem;

                :hover {
                  background-color: ${COLOR.LIGHT_BLUE_400};
                }
              `}
              onClick={editProfileIntro}
            >
              저장
            </Button>
          </section>
        )}
      </div>
    </>
  );
};

export default Introduction;
