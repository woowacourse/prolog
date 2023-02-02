import {useParams} from 'react-router-dom';
import TagManager from 'react-gtm-module';
import {useContext} from 'react';
import {UserContext} from '../../contexts/UserProvider';
import {useGetEssayAnswer} from "../queries/essayanswer";

const useEssayAnswer = () => {
  const { essayAnswerId } = useParams<{ essayAnswerId: string }>();
  // const history = useHistory();
  const { user } = useContext(UserContext);
  // const { openSnackBar } = useSnackBar();
  const { username, userId } = user;

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
  const isCurrentUserAuthor = essayAnswer?.author.username === username;

  return {
    essayAnswer,
    isCurrentUserAuthor,
    isLoading,
  };
};

export default useEssayAnswer;
