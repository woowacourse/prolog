import { useHistory, useParams } from 'react-router-dom';
import TagManager from 'react-gtm-module';

import { useDeleteLevellog, useGetLevellog } from '../queries/levellog';
import { useContext } from 'react';
import { UserContext } from '../../contexts/UserProvider';
import { PATH, ERROR_MESSAGE, ALERT_MESSAGE } from '../../constants';
import { SUCCESS_MESSAGE } from '../../constants/message';
import useSnackBar from '../useSnackBar';

const useLevellog = () => {
  const { id } = useParams<{ id: string }>();
  const history = useHistory();
  const { user } = useContext(UserContext);
  const { openSnackBar } = useSnackBar();
  const { username, userId } = user;

  const { data: levellog, isLoading, refetch: getLevellog } = useGetLevellog(
    { id },
    {
      onSuccess: (levellog) => {
        TagManager.dataLayer({
          dataLayer: {
            event: 'page_view_levellog',
            mine: username === levellog.author?.username,
            user_id: userId,
            username,
            target: levellog.id,
          },
        });
      },
    }
  );
  const isCurrentUserAuthor = levellog?.author.username === username;

  const { mutate: deleteLevellog } = useDeleteLevellog(
    { id },
    {
      onSuccess: () => {
        openSnackBar(SUCCESS_MESSAGE.DELETE_STUDYLOG);
        history.push(PATH.LEVELLOG);
      },
      onError: (error: { code: number }) => {
        alert(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_DELETE_STUDYLOG);
      },
    }
  );

  const goEditTargetPost = () => {
    history.push(`${PATH.LEVELLOG}/${id}/edit`);
  };

  return {
    levellog,
    getLevellog,
    deleteLevellog,
    goEditTargetPost,
    isCurrentUserAuthor,
    isLoading,
  };
};

export default useLevellog;
