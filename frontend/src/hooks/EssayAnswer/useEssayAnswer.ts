import {useHistory, useParams} from 'react-router-dom';
import TagManager from 'react-gtm-module';
import {useContext} from 'react';
import {UserContext} from '../../contexts/UserProvider';
import {
  useDeleteEssayAnswerMutation,
  useGetEssayAnswer
} from "../queries/essayanswer";
import {ALERT_MESSAGE, ERROR_MESSAGE, SUCCESS_MESSAGE} from "../../constants/message";
import {PATH} from "../../constants";
import useSnackBar from "../useSnackBar";

const useEssayAnswer = () => {
  const { essayAnswerId } = useParams<{ essayAnswerId: string }>();
  const { user } = useContext(UserContext);
  const { username, userId } = user;
  const history = useHistory();
  const { openSnackBar } = useSnackBar();

  const { data: essayAnswer, isLoading } = useGetEssayAnswer(
    { essayAnswerId },
    {
      onSuccess: (essayAnswer) => {
        TagManager.dataLayer({
          dataLayer: {
            event: 'page_view_essay_answer',
            mine: username === essayAnswer.author?.username,
            user_id: userId,
            username,
            target: essayAnswer.id,
          },
        });
      },
    }
  );

  const {mutate: deleteEssayAnswerRequest} = useDeleteEssayAnswerMutation({
    onSuccess: () => {
      history.push(PATH.ROADMAP);
      alert(SUCCESS_MESSAGE.DELETE_STUDYLOG);
    },
    onError: (error: { code: number }) => {
      openSnackBar(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_DELETE_ESSAY_ANSWER);
    },
  });

  const deleteEssayAnswer = (essayAnswerId, e) => {
      e.preventDefault();

      deleteEssayAnswerRequest(essayAnswerId);
  }

  const isCurrentUserAuthor = essayAnswer?.author.username === username;

  return {
    essayAnswer,
    deleteEssayAnswer,
    isCurrentUserAuthor,
    isLoading,
  };
};

export default useEssayAnswer;
