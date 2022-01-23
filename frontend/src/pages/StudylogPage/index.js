/** @jsxImportSource @emotion/react */

import { useEffect } from 'react';
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
import useStudylog from '../../hooks/useStudylog';
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

const StudylogPage = () => {
  const history = useHistory();

  const { openSnackBar } = useSnackBar();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const isLoggedIn = !!accessToken;
  const myName = useSelector((state) => state.user.profile.data?.username);

  const { id: studylogId } = useParams();
  const { response: studylog, getData: getStudylog, deleteData: deleteStudylog } = useStudylog({});

  const goProfilePage = (username) => (event) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  const goEditTargetStudylog = (id) => {
    history.push(`${PATH.STUDYLOG}/${id}/edit`);
  };

  const onDeleteStudylog = async (id) => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;

    const hasError = await deleteStudylog(id, accessToken);

    if (hasError) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_STUDYLOG);

      return;
    }

    history.goBack();
  };

  const { mutate: studylogScrap } = useMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostScrap(myName, accessToken, {
        studylogId: studylogId,
      });
    },
    () => {
      getStudylog(studylogId, accessToken);
      openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_SCRAP);
    }
  );

  const { mutate: deleteScrap } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_SCRAP)) return;

      return requestDeleteScrap(myName, accessToken, {
        studylogId: studylogId,
      });
    },
    () => {
      getStudylog(studylogId, accessToken);
      openSnackBar(SNACKBAR_MESSAGE.FAIL_TO_SCRAP);
    }
  );

  const { mutate: studylogLike } = useMutation(
    () => {
      if (!isLoggedIn) {
        alert(ALERT_MESSAGE.NEED_TO_LOGIN);
        return;
      }

      return requestPostLike(accessToken, studylogId);
    },
    () => {
      openSnackBar(SNACKBAR_MESSAGE.SET_LIKE);
      getStudylog(studylogId, accessToken);
    },
    () => openSnackBar(SNACKBAR_MESSAGE.ERROR_SET_LIKE)
  );

  const { mutate: deleteLike } = useMutation(
    () => {
      if (!window.confirm(CONFIRM_MESSAGE.DELETE_LIKE)) return;

      return requestDeleteLike(accessToken, studylogId);
    },
    () => {
      openSnackBar(SNACKBAR_MESSAGE.UNSET_LIKE);
      getStudylog(studylogId, accessToken);
    },
    () => openSnackBar(SNACKBAR_MESSAGE.ERROR_UNSET_LIKE)
  );

  const toggleLike = () => {
    studylog?.liked
      ? debounce(() => {
          deleteLike();
        }, 300)
      : debounce(() => {
          studylogLike();
        }, 300);
  };

  const toggleScrap = () => {
    if (studylog?.scrap) {
      deleteScrap();
      return;
    }

    studylogScrap();
  };

  useEffect(() => {
    getStudylog(studylogId, accessToken);
  }, [accessToken, studylogId]);

  return (
    <div css={MainContentStyle}>
      {myName === studylog?.author?.username && (
        <ButtonList>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={EditButtonStyle}
            alt="수정 버튼"
            onClick={() => goEditTargetStudylog(studylog?.id)}
          >
            수정
          </Button>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={DeleteButtonStyle}
            alt="삭제 버튼"
            onClick={() => onDeleteStudylog(studylog?.id)}
          >
            삭제
          </Button>
        </ButtonList>
      )}
      <Card key={studylog?.id} size="LARGE">
        <CardInner>
          <div>
            <SubHeader>
              <Mission>{studylog?.mission?.name}</Mission>
              <SubHeaderRightContent>
                <IssuedDate>{new Date(studylog?.createdAt).toLocaleString('ko-KR')}</IssuedDate>
              </SubHeaderRightContent>
            </SubHeader>
            <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
              <Title>{studylog?.title}</Title>
              <ViewCount count={studylog?.viewCount} />
            </div>
            <ProfileChip
              imageSrc={studylog?.author?.imageUrl}
              cssProps={ProfileChipStyle}
              onClick={goProfilePage(studylog?.author?.username)}
            >
              {studylog?.author?.nickname}
            </ProfileChip>
          </div>
          <Content>
            <Viewer
              initialValue={studylog?.content}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
          </Content>
          <BottomContainer>
            <Tags>
              {studylog?.tags?.map(({ id, name }) => (
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
                liked={studylog?.liked}
                likesCount={studylog?.likesCount}
                onClick={toggleLike}
              />
              <Scrap scrap={studylog?.scrap} onClick={toggleScrap} />
            </div>
          </BottomContainer>
        </CardInner>
      </Card>
    </div>
  );
};

export default StudylogPage;
