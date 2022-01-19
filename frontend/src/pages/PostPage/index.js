/** @jsxImportSource @emotion/react */

import { useCallback, useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import {
  requestPostScrap,
  requestDeleteScrap,
  requestPostLike,
  requestDeleteLike,
} from '../../service/requests';

import { Button, BUTTON_SIZE, Card, ProfileChip } from '../../components';
import { Viewer } from '@toast-ui/react-editor';

import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';

import {
  ButtonList,
  EditButtonStyle,
  DeleteButtonStyle,
  CardInner,
  IssuedDate,
  Mission,
  ProfileChipStyle,
  SubHeader,
  Tags,
  Title,
  SubHeaderRightContent,
  Content,
  BottomContainer,
} from './styles';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH, SNACKBAR_MESSAGE } from '../../constants';
import { useSelector } from 'react-redux';

import useSnackBar from '../../hooks/useSnackBar';
import useStudyLog from '../../hooks/useStudyLog';
import debounce from '../../utils/debounce';
import { css } from '@emotion/react';
import useMutation from '../../hooks/useMutation';

import Like from '../../components/Reaction/Like';
import Scrap from '../../components/Reaction/Scrap';
import {
  AlignItemsBaseLineStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import ViewCount from '../../components/ViewCount/ViewCount';
import { MainContentStyle } from '../../PageRouter';

const PostPage = () => {
  const history = useHistory();

  const { openSnackBar } = useSnackBar();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const isLoggedIn = !!accessToken;
  const myName = useSelector((state) => state.user.profile.data?.username);

  const { id: postId } = useParams();
  const { response: studyLog, getData, deleteData } = useStudyLog({});

  const getStudyLog = useCallback(() => getData(postId, accessToken), [
    postId,
    accessToken,
    getData,
  ]);
  const deleteStudyLog = useCallback(() => deleteData(postId, accessToken), [
    postId,
    accessToken,
    deleteData,
  ]);

  const goProfilePage = (username) => (event) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  const goEditTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}/edit`);
  };

  const onDeletePost = async (id) => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    const hasError = await deleteStudyLog(id, accessToken);

    if (hasError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_POST);

      return;
    }

    history.goBack();
  };

  const { mutate: postScrap } = useMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostScrap(myName, accessToken, {
        studylogId: postId,
      });
    },
    () => {
      getStudyLog();
      openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_SCRAP);
    }
  );

  const { mutate: deleteScrap } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) return;

      return requestDeleteScrap(myName, accessToken, {
        studylogId: postId,
      });
    },
    () => {
      getStudyLog();
      openSnackBar(SNACKBAR_MESSAGE.FAIL_TO_SCRAP);
    }
  );

  const { mutate: postLike } = useMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostLike(accessToken, postId);
    },
    () => {
      openSnackBar(SNACKBAR_MESSAGE.SET_LIKE);
      getStudyLog();
    },
    () => openSnackBar(SNACKBAR_MESSAGE.ERROR_SET_LIKE)
  );

  const { mutate: deleteLike } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_LIKE)) return;

      return requestDeleteLike(accessToken, postId);
    },
    () => {
      openSnackBar(SNACKBAR_MESSAGE.UNSET_LIKE);
      getStudyLog();
    },
    () => openSnackBar(SNACKBAR_MESSAGE.ERROR_UNSET_LIKE)
  );

  const toggleLike = () => {
    studyLog?.liked
      ? debounce(() => {
          deleteLike();
        }, 300)
      : debounce(() => {
          postLike();
        }, 300);
  };

  const toggleScrap = () => {
    if (studyLog?.scrap) {
      deleteScrap();
      return;
    }

    postScrap();
  };

  useEffect(() => {
    getStudyLog();
  }, [accessToken, postId]);

  return (
    <div css={MainContentStyle}>
      {myName === studyLog?.author?.username && (
        <ButtonList>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={EditButtonStyle}
            alt="수정 버튼"
            onClick={() => goEditTargetPost(studyLog?.id)}
          >
            수정
          </Button>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={DeleteButtonStyle}
            alt="삭제 버튼"
            onClick={() => onDeletePost(studyLog?.id)}
          >
            삭제
          </Button>
        </ButtonList>
      )}
      <Card key={studyLog?.id} size="LARGE">
        <CardInner>
          <div>
            <SubHeader>
              <Mission>{studyLog?.mission?.name}</Mission>
              <SubHeaderRightContent>
                <IssuedDate>{new Date(studyLog?.createdAt).toLocaleString('ko-KR')}</IssuedDate>
              </SubHeaderRightContent>
            </SubHeader>
            <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
              <Title>{studyLog?.title}</Title>
              <ViewCount count={studyLog?.viewCount} />
            </div>
            <ProfileChip
              imageSrc={studyLog?.author?.imageUrl}
              cssProps={ProfileChipStyle}
              onClick={goProfilePage(studyLog?.author?.username)}
            >
              {studyLog?.author?.nickname}
            </ProfileChip>
          </div>
          <Content>
            <Viewer
              initialValue={studyLog?.content}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
          </Content>
          <BottomContainer>
            <Tags>
              {studyLog?.tags?.map(({ id, name }) => (
                <span key={id}>{`#${name} `}</span>
              ))}
            </Tags>
            <div
              css={[
                FlexStyle,
                AlignItemsBaseLineStyle,
                css`
                  > *:not(:last-child) {
                    margin-right: 1rem;
                  }
                `,
              ]}
            >
              <Like
                liked={studyLog?.liked}
                likesCount={studyLog?.likesCount}
                onClick={toggleLike}
              />
              <Scrap scrap={studyLog?.scrap} onClick={toggleScrap} />
            </div>
          </BottomContainer>
        </CardInner>
      </Card>
    </div>
  );
};

export default PostPage;
