/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useHistory } from 'react-router-dom';
import { Card, ProfileChip } from '..';
import { COLOR, PATH } from '../../constants';
import { AlignItemsEndStyle, FlexColumnStyle, FlexStyle } from '../../styles/flex.styles';
import ViewCount from '../ViewCount/ViewCount';

import {
  CardStyle,
  ContentStyle,
  DescriptionStyle,
  MissionStyle,
  TagListStyle,
  ProfileChipLocationStyle,
} from './StudylogLists.styles';

interface Props {
  studylogs: Prolog.Studylog[];
}

const StudylogList = ({ studylogs }: Props) => {
  const history = useHistory();

  const goTargetPost = (id: number) => {
    history.push(`${PATH.POST}/${id}`);
  };

  const goProfilePage = (username: string) => (event: MouseEvent) => {
    event.stopPropagation();

    history.push(`/${username}`);
  };

  return (
    <ul
      css={css`
        > li:not(:last-child) {
          margin-bottom: 1.6rem;
        }
      `}
    >
      {studylogs.map((post) => {
        const { id, author, mission, title, tags, isRead, viewCount } = post;

        return (
          <li key={id}>
            <Card
              size="SMALL"
              cssProps={
                isRead
                  ? css`
                      ${CardStyle};
                      background-color: ${COLOR.LIGHT_GRAY_100};
                    `
                  : CardStyle
              }
              onClick={() => goTargetPost(id)}
            >
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
                <div css={[FlexStyle, FlexColumnStyle, AlignItemsEndStyle]}>
                  <ProfileChip
                    imageSrc={author.imageUrl}
                    cssProps={ProfileChipLocationStyle}
                    onClick={goProfilePage(author.username)}
                  >
                    {author.nickname}
                  </ProfileChip>
                  <ViewCount count={viewCount} />
                </div>
              </div>
            </Card>
          </li>
        );
      })}
    </ul>
  );
};

export default StudylogList;
