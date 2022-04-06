/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useHistory } from 'react-router-dom';

import StudylogItem from '../Items/StudylogItem';
import { PATH } from '../../enumerations/path';
import { Studylog } from '../../models/Studylogs';

interface Props {
  studylogs: Studylog[];
}

const StudylogList = ({ studylogs }: Props) => {
  const history = useHistory();

  const goTargetPost = (id: number) => {
    history.push(`${PATH.STUDYLOGS}/${id}`);
  };

  const goProfilePage = (username: string) => (event?: MouseEvent) => {
    event?.stopPropagation();

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
      {studylogs.map((studylog) => {
        return (
          <li key={studylog.id}>
            <StudylogItem
              studylog={studylog}
              onClick={() => goTargetPost(studylog.id)}
              onProfileClick={goProfilePage(studylog.author.username)}
            />
          </li>
        );
      })}
    </ul>
  );
};

export default StudylogList;
