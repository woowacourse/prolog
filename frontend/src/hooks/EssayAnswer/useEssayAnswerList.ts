import { useGetQuizAnswerList, useGetQuiz } from '../queries/essayanswer';
import { useParams } from 'react-router-dom';

export const useEssayAnswerList = () => {
  const { quizId } = useParams<{ quizId: string }>();

  const { data: essayAnswers } = useGetQuizAnswerList({ quizId });
  const { data: quiz } = useGetQuiz({ quizId })

  return { quiz, essayAnswers };
};
