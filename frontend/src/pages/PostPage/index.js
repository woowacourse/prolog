import { useHistory, useParams } from 'react-router';
import useFetch from '../../hooks/useFetch';
import { requestGetPost } from '../../service/requests';

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
  ScrapButtonStyle,
  BottomContainer,
} from './styles';
import useNotFound from '../../hooks/useNotFound';
import { ALERT_MESSAGE, CONFIRM_MESSAGE, PATH } from '../../constants';
import { useSelector } from 'react-redux';
import usePost from '../../hooks/usePost';
import scrapIcon from '../../assets/images/scrap.svg';
import unScrapIcon from '../../assets/images/scrap_filled.svg';
import useSnackBar from '../../hooks/useSnackBar';

const PostPage = () => {
  const history = useHistory();
  const { id: postId } = useParams();
  const [post, errorStatus] = useFetch({}, () => requestGetPost(postId));
  const { NotFound } = useNotFound();

  const { deleteData: deletePost } = usePost({});
  const { openSnackBar } = useSnackBar();

  const accessToken = useSelector((state) => state.user.accessToken.data);
  const myName = useSelector((state) => state.user.profile.data?.username);

  const { id, author, createdAt, mission, title, tags, content } = post;

  if (errorStatus) {
    switch (errorStatus) {
      case 2004:
        return <NotFound />;
      default:
        return;
    }
  }

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

  const toggleScrap = () => {
    openSnackBar('스크랩을 완료했습니다');
  };

  return (
    <>
      {myName === author?.username && (
        <ButtonList>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            css={EditButtonStyle}
            alt="수정 버튼"
            onClick={() => goEditTargetPost(id)}
          >
            수정
          </Button>
          <Button
            size={BUTTON_SIZE.X_SMALL}
            type="button"
            css={DeleteButtonStyle}
            alt="삭제 버튼"
            onClick={() => onDeletePost(id)}
          >
            삭제
          </Button>
        </ButtonList>
      )}
      <Card key={id} size="LARGE">
        <CardInner>
          <div>
            <SubHeader>
              <Mission>{mission?.name}</Mission>
              <SubHeaderRightContent>
                <IssuedDate>{new Date(createdAt).toLocaleString('ko-KR')}</IssuedDate>
              </SubHeaderRightContent>
            </SubHeader>
            <Title>{title}</Title>
            <ProfileChip
              imageSrc={author?.imageUrl}
              css={ProfileChipStyle}
              onClick={goProfilePage(author?.username)}
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
            <div>
              {true ? (
                <Button
                  type="button"
                  size="X_SMALL"
                  icon={scrapIcon}
                  alt="스크랩 아이콘"
                  css={ScrapButtonStyle}
                  onClick={toggleScrap}
                />
              ) : (
                <Button
                  type="button"
                  size="X_SMALL"
                  icon={unScrapIcon}
                  alt="스크랩 아이콘"
                  css={ScrapButtonStyle}
                  onClick={toggleScrap}
                />
              )}
            </div>
          </BottomContainer>
        </CardInner>
      </Card>
    </>
  );
};

export default PostPage;
