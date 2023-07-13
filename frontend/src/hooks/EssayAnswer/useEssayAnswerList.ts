import { useGetEssayAnswerList, useGetQuiz } from '../queries/essayanswer';
import { useParams } from 'react-router-dom';

export const useEssayAnswerList = () => {
  const { quizId } = useParams<{ quizId: string }>();

  const { data: essayAnswers } = useGetEssayAnswerList({ quizId });
  const { data: quiz } = useGetQuiz({ quizId })

  return { quiz, essayAnswers };
};
