import { useParams } from 'react-router';
import { useHistory } from 'react-router';
import useFetch from '../../hooks/useFetch';
import { requestGetPost } from '../../service/requests';

import { Card, ProfileChip } from '../../components';
import { Viewer } from '@toast-ui/react-editor';

import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

import { CardInner, SubHeader, Mission, Title, Tags, IssuedDate, ProfileChipStyle } from './styles';

const PostPage = () => {
  const history = useHistory();

  const { id: postId } = useParams();
  const [post, getPostError] = useFetch({}, () => requestGetPost(postId));
  const { id, author, createdAt, mission, title, tags, content } = post;

  if (getPostError) {
    <>해당 글을 찾을 수 없습니다.</>;
  }

  const goProfilePage = (username) => (event) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  return (
    <Card key={id} size="LARGE">
      <CardInner>
        <div>
          <SubHeader>
            <Mission>{mission?.name}</Mission>
            <IssuedDate>{new Date(createdAt).toLocaleString('ko-KR')}</IssuedDate>
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
        <div>
          <Viewer initialValue={content} />
        </div>
        <Tags>
          {tags?.map(({ id, name }) => (
            <span key={id}>{`#${name} `}</span>
          ))}
        </Tags>
      </CardInner>
    </Card>
  );
};

export default PostPage;
