/** @jsxImportSource @emotion/react */

import { useEffect } from 'react';
import { useHistory, useParams } from 'react-router';
import {
  requestGetPost,
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
import usePost from '../../hooks/usePost';

import useSnackBar from '../../hooks/useSnackBar';
import debounce from '../../utils/debounce';
import { css } from '@emotion/react';
import useMutation from '../../hooks/useMutation';
import useRequest from '../../hooks/useRequest';
import Like from '../../components/Reaction/Like';
import Scrap from '../../components/Reaction/Scrap';

const PostPage = () => {
  const history = useHistory();
  const { id: postId } = useParams();

  const { response: post, fetchData: fetchPost } = useRequest({}, () =>
    requestGetPost(postId, accessToken)
  );

  const { deleteData: deletePost } = usePost({});
  const { openSnackBar } = useSnackBar();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const isLoggedIn = !!accessToken;
  const myName = useSelector((state) => state.user.profile.data?.username);

  const goProfilePage = (username) => (event) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  const goEditTargetPost = (id) => {
    history.push(`${PATH.POST}/${id}/edit`);
  };

  const onDeletePost = async (id) => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_POST)) return;

    const hasError = await deletePost(id, accessToken);

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
      fetchPost();
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
      fetchPost();
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
      fetchPost();
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
      fetchPost();
    },
    () => openSnackBar(SNACKBAR_MESSAGE.ERROR_UNSET_LIKE)
  );

  const toggleLike = () => {
    post?.liked
      ? debounce(() => {
          deleteLike();
        }, 300)
      : debounce(() => {
          postLike();
        }, 300);
  };

  const toggleScrap = () => {
    if (post?.scrap) {
      deleteScrap();
      return;
    }

    postScrap();
  };

  useEffect(() => {
    fetchPost();
  }, [accessToken, postId]);

  return (
    <>
      {myName === post?.author?.username && (
        <ButtonList>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={EditButtonStyle}
            alt="수정 버튼"
            onClick={() => goEditTargetPost(post?.id)}
          >
            수정
          </Button>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            cssProps={DeleteButtonStyle}
            alt="삭제 버튼"
            onClick={() => onDeletePost(post?.id)}
          >
            삭제
          </Button>
        </ButtonList>
      )}
      <Card key={post?.id} size="LARGE">
        <CardInner>
          <div>
            <SubHeader>
              <Mission>{post?.mission?.name}</Mission>
              <SubHeaderRightContent>
                <IssuedDate>{new Date(post?.createdAt).toLocaleString('ko-KR')}</IssuedDate>
              </SubHeaderRightContent>
            </SubHeader>
            <Title>{post?.title}</Title>
            <ProfileChip
              imageSrc={post?.author?.imageUrl}
              cssProps={ProfileChipStyle}
              onClick={goProfilePage(post?.author?.username)}
            >
              {post?.author?.nickname}
            </ProfileChip>
          </div>
          <Content>
            <Viewer
              initialValue={post?.content}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
          </Content>
          <BottomContainer>
            <Tags>
              {post?.tags?.map(({ id, name }) => (
                <span key={id}>{`#${name} `}</span>
              ))}
            </Tags>
            {/* TODO: 해당 css 적용 부분, 다른 사용자 리액션 pr 머지 후 삭제 및 flex 속성 적용 */}
            <div
              css={css`
                display: flex;
                align-items: baseline;

                > *:not(:last-child) {
                  margin-right: 1rem;
                }
              `}
            >
              <Like liked={post?.liked} likesCount={post?.likesCount} onClick={toggleLike} />
              <Scrap scrap={post?.scrap} onClick={toggleScrap} />
            </div>
          </BottomContainer>
        </CardInner>
      </Card>
    </>
  );
};

export default PostPage;
