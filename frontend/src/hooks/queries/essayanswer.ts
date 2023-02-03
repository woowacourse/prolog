import {useMutation, useQuery} from "react-query";
import {
  createNewEssayAnswerRequest,
  requestGetEssayAnswer,
  requestGetEssayAnswerList,
  requestGetQuizAsync
} from "../../apis/essayanswers";
import {EssayAnswerRequest, EssayAnswerResponse} from "../../models/EssayAnswers";

import {AxiosError} from 'axios';
import {useHistory} from 'react-router-dom';
import {ALERT_MESSAGE, PATH} from '../../constants';
import ERROR_CODE from '../../constants/errorCode';
import useSnackBar from '../useSnackBar';
import REACT_QUERY_KEY from "../../constants/reactQueryKey";
import {Quiz} from "../../models/Keywords";

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

export const useGetEssayAnswer = (
  { essayAnswerId },
  {
    onSuccess = (essayAnswer: EssayAnswerResponse) => {},
    onError = () => {}
  } = {}
) => {
  const history = useHistory();
  const { openSnackBar } = useSnackBar();
  return useQuery<EssayAnswerResponse>([REACT_QUERY_KEY.ESSAY_ANSWER, essayAnswerId], () => requestGetEssayAnswer(essayAnswerId), {
    onSuccess: (essayAnswer: EssayAnswerResponse) => {
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
  });
};

export const useGetEssayAnswerList = (
  { quizId },
  {
    onSuccess = (essayAnswer: EssayAnswerResponse[]) => {},
    onError = () => {}
  } = {}
) => {
  return useQuery<EssayAnswerResponse[]>([REACT_QUERY_KEY.ESSAY_ANSWER_LIST, quizId], () => requestGetEssayAnswerList(quizId), {
    onSuccess: (essayAnswer: EssayAnswerResponse[]) => {
      onSuccess?.(essayAnswer);
    },
    onError: (error) => {},
    refetchOnWindowFocus: false,
    retry: false,
  });
};

export const useGetQuiz = (
  { quizId },
  {
    onSuccess = (quiz: Quiz) => {},
    onError = () => {}
  } = {}
) => {
  return useQuery<Quiz>([REACT_QUERY_KEY.QUIZ, quizId], () => requestGetQuizAsync(quizId), {
    onSuccess: (quiz: Quiz) => {
      onSuccess?.(quiz);
    },
    onError: (error) => {},
    refetchOnWindowFocus: false,
    retry: false,
  });
};
