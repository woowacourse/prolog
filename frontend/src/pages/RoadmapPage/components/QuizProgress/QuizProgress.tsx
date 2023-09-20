/** @jsxImportSource @emotion/react */

import type { KeywordResponse } from '../../../../models/Keywords';
import { css } from '@emotion/react';
import { CircularProgress } from './QuizProgress.styles';

interface QuizProgressProps {
  doneCount: KeywordResponse['doneQuizCount'];
  totalCount: KeywordResponse['totalQuizCount'];
}

export function MaterialSymbolsCheckSmall(props: React.SVGProps<SVGSVGElement>) {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" {...props}>
      <path fill="white" d="m10 16.4l-4-4L7.4 11l2.6 2.6L16.6 7L18 8.4l-8 8Z"></path>
    </svg>
  );
}

const QuizProgress = (props: QuizProgressProps) => {
  const { doneCount, totalCount } = props;

  if (totalCount === 0) return null;

  const ratio = doneCount / totalCount;

  return ratio < 1 ? (
    <CircularProgress value={ratio} />
  ) : (
    <div
      css={css`
        width: 16px;
        height: 16px;
        border-radius: 50%;
        background-color: hsl(147, 100%, 50%);
      `}
    >
      <MaterialSymbolsCheckSmall width="100%" height="100%" />
    </div>
  );
};

export default QuizProgress;
