/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { KeywordResponse } from '../../../../models/Keywords';
import { hsl, ImportanceColors, KeywordColors, toAdjustedLightness, toHue } from '../../colors';
import QuizProgress from '../QuizProgress/QuizProgress';

type Keyword = KeywordResponse;

type SubKeywordProps = {
  hue: number;
  keyword: Keyword
  onClick?: (keyword: Keyword) => void;
};

const SubKeyword = (props: SubKeywordProps) => {
  const { hue, keyword, onClick } = props;

  return (
    <div
      css={css`
        background: ${hsl(toHue(KeywordColors.SUB_KEYWORD, hue))};
        height: 100%;
        display: grid;
        grid-template-columns: 1fr;
        align-items: center;
        font-size: 12px;
        font-family: 'TheJamsil5Bold';
        font-weight: bold;
        cursor: pointer;
        border-radius: 4px;
        overflow: hidden;

        &:hover {
          background: ${hsl(toAdjustedLightness(toHue(KeywordColors.SUB_KEYWORD, hue), -0.1))};
        }

        & > * {
          grid-area: 1 / 1 / 1 / 1;
        }
      `}
      title={keyword.name}
      onClick={() => onClick?.(keyword)}
    >
      <div
        css={css`
          height: 100%;
          display: flex;
          align-items: flex-start;
          justify-content: flex-end;
          position: absolute;
          top: -5px;
          right: -5px;
        `}
      >
        <QuizProgress totalCount={keyword.totalQuizCount} doneCount={keyword.doneQuizCount} />
      </div>
      <div
        css={css`
          width: 10px;
          height: 100%;
          background: ${hsl(ImportanceColors[keyword.importance])};
          margin-right: auto;
          box-shadow: inset 1px 0 1px 1px rgba(0, 0, 0, 0.05);
        `}
      />
      <div
        css={css`
          justify-self: center;
          text-align: center;
          width: 80%;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        `}
      >
        {keyword.name}
      </div>
    </div>
  );
};

export default SubKeyword;
