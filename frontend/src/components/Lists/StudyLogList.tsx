/** @jsxImportSource @emotion/react */
import { useHistory } from 'react-router';
import { Card, ProfileChip } from '..';
import { PATH } from '../../constants';

import {
  CardStyle,
  ContentStyle,
  DescriptionStyle,
  MissionStyle,
  TagListStyle,
  ProfileChipLocationStyle,
} from './StudyLogList.styles';

interface Props {
  studylogs: Prolog.StudyLog[];
}

const StudyLogList = ({ studylogs }: Props) => {
  const history = useHistory();

  const goTargetPost = (id: number) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goProfilePage = (username: string) => (event: MouseEvent) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  return (
    <>
      {studylogs.map((post) => {
        const { id, author, mission, title, tags } = post;

        return (
          <Card key={id} size="SMALL" cssProps={CardStyle} onClick={() => goTargetPost(id)}>
            <div css={ContentStyle}>
              <div css={DescriptionStyle}>
                <p css={MissionStyle}>{mission.name}</p>
                <h3>{title}</h3>
                <ul css={TagListStyle}>
                  {tags?.map(({ id, name }) => (
                    <span key={id}>{`#${name} `}</span>
                  ))}
                </ul>
              </div>
              <ProfileChip
                imageSrc={author.imageUrl}
                cssProps={ProfileChipLocationStyle}
                onClick={goProfilePage(author.username)}
              >
                {author.nickname}
              </ProfileChip>
            </div>
          </Card>
        );
      })}
    </>
  );
};

export default StudyLogList;
