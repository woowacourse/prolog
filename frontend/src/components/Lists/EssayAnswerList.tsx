/** @jsxImportSource @emotion/react */
import {css} from '@emotion/react';
import EssayAnswerItem from "../Items/EssayAnswerItem";
import { NoDefaultHoverLink } from '../Items/EssayAnswerItem.styles';

const EssayAnswerList = ({ essayAnswers }) => {
  return (
    <ul
      css={css`
        > li:not(:last-child) {
          margin-bottom: 1.6rem;
        }
      `}
    >
      {essayAnswers.map((essayAnswer) => (
        <li key={essayAnswer.id}>
          <NoDefaultHoverLink to={`/essay-answers/${essayAnswer.id}`}>
            <EssayAnswerItem essayAnswer={essayAnswer} />
          </NoDefaultHoverLink>
        </li>
      ))}
    </ul>
  );
};

export default EssayAnswerList;
