import { useParams, useHistory } from 'react-router';
import useFetch from '../../hooks/useFetch';
import { requestGetPost } from '../../service/requests';

import { Card, ProfileChip } from '../../components';
import { Viewer } from '@toast-ui/react-editor';

import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

import { CardInner, SubHeader, Mission, Title, Tags, IssuedDate, ProfileChipStyle } from './styles';
import useNotFound from '../../hooks/useNotFound';

const PostPage = () => {
  const history = useHistory();

  const { id: postId } = useParams();
  const [post, errorStatus] = useFetch({}, () => requestGetPost(postId));
  const { NotFound } = useNotFound();

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
