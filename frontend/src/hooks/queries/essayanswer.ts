import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import {
  createNewEssayAnswerRequest,
  requestDeleteEssayAnswer,
  requestEditEssayAnswer,
  requestGetEssayAnswer,
  requestGetEssayAnswers,
  requestGetQuizAnswers,
  requestGetQuizAsync,
  requestGetQuizzes,
} from '../../apis/essayanswers';
import { ResponseError } from '../../apis/studylogs';
import { ALERT_MESSAGE, PATH } from '../../constants';
import ERROR_CODE from '../../constants/errorCode';
import { ERROR_MESSAGE, SUCCESS_MESSAGE } from '../../constants/message';
import REACT_QUERY_KEY from '../../constants/reactQueryKey';
import {
  EssayAnswer,
  EssayAnswerFilterRequest,
  EssayAnswerRequest,
  EssayAnswerResponse,
} from '../../models/EssayAnswers';
import { Quiz } from '../../models/Keywords';
import useSnackBar from '../useSnackBar';

export const useCreateNewEssayAnswerMutation = ({
  onSuccess = () => {},
  onError = (error: { code: number }) => {},
} = {}) =>
  useMutation((body: EssayAnswerRequest) => createNewEssayAnswerRequest(body), {
    onSuccess: () => {
      onSuccess?.();
    },
    onError: (error: { code: number }) => {
      onError?.(error);
    },
  });

export const useEditEssayAnswer = ({ essayAnswerId }: { essayAnswerId: number }) => {
  const history = useHistory();

  return useMutation((data: { answer: string }) => requestEditEssayAnswer(essayAnswerId, data), {
    onSuccess: () => {
      alert(SUCCESS_MESSAGE.EDIT_POST);
      history.push(`/essay-answers/${essayAnswerId}`);
    },

    onError: (error: ResponseError) => {
      alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.FAIL_TO_EDIT_STUDYLOG);
    },
  });
};

export const useGetEssayAnswer = (
  { essayAnswerId },
  { onSuccess = (essayAnswer: EssayAnswer) => {}, onError = () => {} } = {}
) => {
  const history = useHistory();
  const { openSnackBar } = useSnackBar();
  return useQuery<EssayAnswer>(
    [REACT_QUERY_KEY.ESSAY_ANSWER, essayAnswerId],
    () => requestGetEssayAnswer(essayAnswerId),
    {
      onSuccess: (essayAnswer: EssayAnswer) => {
        onSuccess?.(essayAnswer);
      },
      onError: (error) => {
        const { response } = (error as unknown) as AxiosError;

        if (response?.data.code === ERROR_CODE.NO_CONTENT) {
          openSnackBar(ALERT_MESSAGE.NO_EXIST_POST);
          history.push(PATH.ROADMAP);
        }
      },
      refetchOnWindowFocus: false,
      retry: false,
    }
  );
};

export const useDeleteEssayAnswerMutation = ({
  onSuccess = () => {},
  onError = (error: { code: number }) => {},
} = {}) =>
  useMutation((essayAnswerId: number) => requestDeleteEssayAnswer(essayAnswerId), {
    onSuccess: () => {
      onSuccess?.();
    },
    onError: (error: { code: number }) => {
      onError?.(error);
    },
  });

export const useGetQuizAnswerList = (
  { quizId },
  { onSuccess = (essayAnswer: EssayAnswer[]) => {}, onError = () => {} } = {}
) => {
  return useQuery<EssayAnswer[]>(
    [REACT_QUERY_KEY.QUIZ_ANSWERS, quizId],
    () => requestGetQuizAnswers(quizId),
    {
      onSuccess: (essayAnswer: EssayAnswer[]) => {
        onSuccess?.(essayAnswer);
      },
      onError: (error) => {},
      refetchOnWindowFocus: false,
      retry: false,
    }
  );
};

export const useGetEssayAnswers = (filter: EssayAnswerFilterRequest) => {
  return useQuery<EssayAnswerResponse>([REACT_QUERY_KEY.ESSAY_ANSWER_FILTER_LIST, filter], () =>
    requestGetEssayAnswers(filter)
  );
};

export const useGetQuiz = (
  { quizId },
  { onSuccess = (quiz: Quiz) => {}, onError = () => {} } = {}
) => {
  return useQuery<Quiz>([REACT_QUERY_KEY.QUIZ, quizId], () => requestGetQuizAsync(quizId), {
    onSuccess: (quiz: Quiz) => {
      onSuccess?.(quiz);
    },
    refetchOnWindowFocus: false,
    retry: false,
  });
};

export const useGetQuizzes = ({ curriculumId }: { curriculumId: number }) => {
  return useQuery<Array<{ id: number; question: string }>>([REACT_QUERY_KEY.QUIZZES], () =>
    requestGetQuizzes(curriculumId)
  );
};
