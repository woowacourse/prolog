/** @jsxImportSource @emotion/react */

import { useContext, useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import {
  requestPostScrap,
  requestDeleteScrap,
  requestPostLike,
  requestDeleteLike,
  requestGetStudylog,
  requestDeleteStudylog,
  requestStudylogLike,
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
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  ERROR_MESSAGE,
  PATH,
  SNACKBAR_MESSAGE,
} from '../../constants';

import useSnackBar from '../../hooks/useSnackBar';

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
import { UserContext } from '../../contexts/UserProvider';
import useRequest from '../../hooks/useRequest';

import defaultProfileImage from '../../assets/images/no-profile-image.png';

const PostPage = () => {
  const history = useHistory();

  const { openSnackBar } = useSnackBar();

  const { user } = useContext(UserContext);
  const { accessToken, isLoggedIn, username } = user;

  const { id } = useParams();

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

  const {
    author = null,
    mission = {},
    title = '',
    content = '',
    tags = [],
    createdAt = null,
    viewCount = 0,
    liked = false,
    likesCount = 0,
    scrap = false,
  } = studylog;

  const goAuthorProfilePage = (event) => {
    event.stopPropagation();

    if (!author) {
      return;
    }

    history.push(`/${author?.username}`);
  };

  const goEditTargetPost = () => {
    history.push(`${PATH.POST}/${id}/edit`);
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
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={EditButtonStyle}
            alt="수정 버튼"
            onClick={goEditTargetPost}
          >
            수정
          </Button>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={DeleteButtonStyle}
            alt="삭제 버튼"
            onClick={deleteStudylog}
          >
            삭제
          </Button>
        </ButtonList>
      )}
      <Card key={id} size="LARGE">
        <CardInner>
          <div>
            <SubHeader>
              <Mission>{mission.name}</Mission>
              <SubHeaderRightContent>
                <IssuedDate>{new Date(createdAt).toLocaleString('ko-KR')}</IssuedDate>
              </SubHeaderRightContent>
            </SubHeader>
            <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
              <Title>{title}</Title>
              <ViewCount count={viewCount} />
            </div>
            <ProfileChip
              imageSrc={author?.imageUrl || defaultProfileImage}
              cssProps={ProfileChipStyle}
              onClick={goAuthorProfilePage}
            >
              {author?.nickname}
            </ProfileChip>
          </div>
          <Content>
            <Viewer
              initialValue={content}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
          </Content>
          <BottomContainer>
            <Tags>
              {tags?.map(({ id, name }) => (
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
              <Like liked={liked} likesCount={likesCount} onClick={toggleLike} />
              <Scrap scrap={scrap} onClick={toggleScrap} />
            </div>
          </BottomContainer>
        </CardInner>
      </Card>
    </div>
  );
};

export default PostPage;
