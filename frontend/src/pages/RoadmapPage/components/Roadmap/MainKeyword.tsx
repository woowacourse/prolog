/** @jsxImportSource @emotion/react */

import { css } from "@emotion/react";
import { PropsWithChildren } from "react";
import { KeywordResponse } from "../../../../models/Keywords";
import { hsl, KeywordColors, toAdjustedLightness, toHue } from "../../colors";

type Keyword = KeywordResponse;

type MainKeywordProps = PropsWithChildren<{
  hue: number;
  keyword: Keyword;
  onClick?: (keyword: KeywordResponse) => void;
}>;

const MainKeyword = (props: MainKeywordProps) => {
  const { keyword, onClick, hue } = props;

  return (
    <div
      css={css`
        background: ${hsl(toHue(KeywordColors.MAIN_KEYWORD, hue))};
        border: 4px solid ${hsl(toHue(KeywordColors.BORDER, hue))};
        border-radius: 8px;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        font-weight: bold;
        font-family: 'TheJamsil5Bold';
        color: white;

        text-align: center;
        width: 100%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;

        &:hover {
          background: ${hsl(toAdjustedLightness(toHue(KeywordColors.MAIN_KEYWORD, hue), -0.1))};
        }
      `}
      onClick={() => onClick?.(keyword)}
    >
      {keyword.name}
    </div>
  );
};

export default MainKeyword;
