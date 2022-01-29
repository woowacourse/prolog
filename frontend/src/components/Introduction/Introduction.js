/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';

import useFetch from '../../hooks/useFetch';
import { requestGetProfileIntroduction } from '../../service/requests';
import { COLOR } from '../../constants';

// Markdown Parser
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import { Editor, Viewer } from '@toast-ui/react-editor';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';
import { useState } from 'react';

const Introduction = () => {
  const { username } = useParams();
  const [isEditing, setIsEditing] = useState(false);

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const loginName = useSelector((state) => state.user.profile.data?.username);

  const isOwner = username === loginName;

  const [response] = useFetch({ text: '' }, requestGetProfileIntroduction(username));
  const data = response.text;

  return (
    <>
      <div
        css={[
          !isOwner && data.length === 0
            ? css`
                display: none;
              `
            : css`
                width: 100%;
                min-height: 12rem;
                max-height: fit-content;

                padding: 1.6rem 2rem 1.8rem;
                margin-bottom: 2rem;

                position: relative;

                background-color: ${COLOR.WHITE};
                border: 1px solid ${COLOR.LIGHT_GRAY_200};
                border-radius: 2rem;

                display: flex;
                justify-content: center;
                align-items: center;
              `,
          isOwner &&
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
        {isOwner && !isEditing && data.length === 0 && (
          <p
            css={css`
              margin: 0;
            `}
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
        {!isEditing && data.length !== 0 && (
          <div
            css={css`
              width: 100%;

              && * {
                margin: 0;
                line-height: 2;
              }

              h1 {
                border-bottom: 2px solid ${COLOR.LIGHT_GRAY_300};
              }

              h3 {
                font-size: 1.8rem;
              }

              a {
                text-decoration: none;
                font-size: 1.6rem;
              }

              p {
                font-size: 1.4rem;
              }

              && li::before {
                margin-top: 8px;
                transform: translateY(70%);
              }
            `}
          >
            <Viewer
              initialValue={data}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
          </div>
        )}
        {isEditing && (
          <>
            <Editor />
            <button type="button">저장</button>
          </>
        )}
      </div>
    </>
  );
};

export default Introduction;
