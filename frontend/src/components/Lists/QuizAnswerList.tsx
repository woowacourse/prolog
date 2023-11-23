/** @jsxImportSource @emotion/react */
import {css} from '@emotion/react';
import EssayAnswerItem from "../Items/EssayAnswerItem";
import { NoDefaultHoverLink } from '../Items/EssayAnswerItem.styles';
import { EssayAnswer } from '../../models/EssayAnswers';

interface QuizAnswerListProps {
  essayAnswers: EssayAnswer[];
  showQuizTitle?: boolean;
}

const QuizAnswerList = (props: QuizAnswerListProps) => {
  const { essayAnswers, showQuizTitle = false } = props;

  return (
    <ul
      css={css`
        > li:not(:last-child) {
          margin-bottom: 1.6rem;
        }
      `}
    >
      {essayAnswers.map(({ id, answer, author, quiz: { question } }) => (
        <li key={id}>
          <NoDefaultHoverLink to={`/essay-answers/${id}`}>
            <EssayAnswerItem
              answer={answer}
              author={author}
              title={question}
              showTitle={showQuizTitle}
            />
          </NoDefaultHoverLink>
        </li>
      ))}
    </ul>
  );
};

export default QuizAnswerList;
