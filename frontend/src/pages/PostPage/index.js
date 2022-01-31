/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import Content from './Content';
import { Button, BUTTON_SIZE } from '../../components';
import { ButtonList, EditButtonStyle, DeleteButtonStyle } from './styles';

import { MainContentStyle } from '../../PageRouter';
import { UserContext } from '../../contexts/UserProvider';
import useSnackBar from '../../hooks/useSnackBar';
import useRequest from '../../hooks/useRequest';
import useMutation from '../../hooks/useMutation';
import debounce from '../../utils/debounce';

import {
  requestPostScrap,
  requestDeleteScrap,
  requestDeleteLike,
  requestGetStudylog,
  requestDeleteStudylog,
  requestStudylogLike,
} from '../../service/requests';

import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  ERROR_MESSAGE,
  PATH,
  SNACKBAR_MESSAGE,
} from '../../constants';

const PostPage = () => {
  const { id } = useParams();
  const history = useHistory();

  const { user } = useContext(UserContext);
  const { accessToken, isLoggedIn, username } = user;

  const { openSnackBar } = useSnackBar();

  const { response: studylog, fetchData: getStudylog } = useRequest({}, () =>
    requestGetStudylog({ id, accessToken })
  );

  const { mutate: deleteStudylog } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

      return requestDeleteStudylog({ id, accessToken });
    },
    {
      onError: (error) => {
        alert(ERROR_MESSAGE[error.code] ?? ALERT_MESSAGE.FAIL_TO_DELETE_POST);
      },
    }
  );

  const { author = null, liked = false, scrap = false } = studylog;

  const goAuthorProfilePage = (event) => {
    event.stopPropagation();

    if (!author) {
      return;
    }

    history.push(`/${author?.username}`);
  };

  const goEditTargetPost = () => {
    history.push(`${PATH.STUDYLOG}/${id}/edit`);
  };

  const { mutate: postScrap } = useMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostScrap({ username, accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_SCRAP);
      },
    }
  );

  const { mutate: deleteScrap } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) return;

      return requestDeleteScrap({ username, accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.DELETE_SCRAP);
      },
    }
  );

  const { mutate: postLike } = useMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestStudylogLike({ accessToken, id });
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.SET_LIKE);
      },
      onError: () => openSnackBar(SNACKBAR_MESSAGE.ERROR_SET_LIKE),
    }
  );

  const { mutate: deleteLike } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_LIKE)) return;

      return requestDeleteLike(accessToken, id);
    },
    {
      onSuccess: async () => {
        await getStudylog();
        openSnackBar(SNACKBAR_MESSAGE.UNSET_LIKE);
      },
      onError: () => openSnackBar(SNACKBAR_MESSAGE.ERROR_UNSET_LIKE),
    }
  );

  const toggleLike = () => {
    liked
      ? debounce(() => {
          deleteLike();
        }, 300)
      : debounce(() => {
          postLike();
        }, 300);
  };

  const toggleScrap = () => {
    if (scrap) {
      deleteScrap();
      return;
    }

    postScrap();
  };

  useEffect(() => {
    getStudylog();
  }, [accessToken, id]);

  return (
    <div css={MainContentStyle}>
      {username === author?.username && (
        <ButtonList>
          {[
            { title: '수정', cssProps: EditButtonStyle, onClick: goEditTargetPost },
            { title: '삭제', cssProps: DeleteButtonStyle, onClick: deleteStudylog },
          ].map(({ title, cssProps, onClick }) => (
            <Button
              key={title}
              size={BUTTON_SIZE.X_SMALL}
              type="button"
              cssProps={cssProps}
              onClick={onClick}
            >
              {title}
            </Button>
          ))}
        </ButtonList>
      )}
      <Content
        studylog={studylog}
        toggleLike={toggleLike}
        toggleScrap={toggleScrap}
        goAuthorProfilePage={goAuthorProfilePage}
      />
    </div>
  );
};

export default PostPage;
