import { useParams } from 'react-router';
import { Card, ProfileChip } from '../../components';
import useFetch from '../../hooks/useFetch';
import { requestGetPost } from '../../service/requests';
import {
  CardInner,
  SubHeader,
  Mission,
  Title,
  Content,
  Tags,
  IssuedDate,
  ProfileChipStyle,
} from './styles';

const PostPage = () => {
  const { id: postId } = useParams();
  const [post, getPostError] = useFetch({}, () => requestGetPost(postId));
  const { id, author, createdAt, mission, title, tags, content } = post;

  if (getPostError) {
    <>해당 글을 찾을 수 없습니다.</>;
  }

  //   {
  //     localeMatcher?: "best fit" | "lookup";
  //     weekday?: "long" | "short" | "narrow";
  //     era?: "long" | "short" | "narrow";
  //     year?: "numeric" | "2-digit";
  //     month?: "numeric" | "2-digit" | "long" | "short" | "narrow";
  //     day?: "numeric" | "2-digit";
  //     hour?: "numeric" | "2-digit";
  //     minute?: "numeric" | "2-digit";
  //     second?: "numeric" | "2-digit";
  //     timeZoneName?: "long" | "short";
  //     formatMatcher?: "best fit" | "basic";
  //     hour12?: boolean;
  //     timeZone?: string;
  // }

  return (
    <Card key={id} size="LARGE">
      <CardInner>
        <div>
          <SubHeader>
            <Mission>{mission?.name}</Mission>
            <IssuedDate>{new Date(createdAt).toLocaleString('ko-KR')}</IssuedDate>
          </SubHeader>
          <Title>{title}</Title>
          <ProfileChip imageSrc={author?.imageUrl} css={ProfileChipStyle}>
            {author?.nickname}
          </ProfileChip>
        </div>
        <Content>{content}</Content>
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
