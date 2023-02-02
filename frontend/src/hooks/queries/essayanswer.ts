import {useMutation} from "react-query";
import {createNewEssayAnswerRequest} from "../../apis/essayanswers";
import {EssayAnswerRequest} from "../../models/EssayAnswers";

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
