/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';
import { css } from '@emotion/react';

import { Chip } from '..';
import { PATH } from '../../enumerations/path';
import {
  AlignItemsCenterStyle,
  FlexColumnStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import {
  ContainerStyle,
  TopContainerStyle,
  ProfileAreaStyle,
  UserReactionIconStyle,
  getRandomBgColorStyle,
  getHorizontalGapStyle,
  DateAreaStyle,
  BottomContainerStyle,
  ContentsAreaStyle,
} from './PopularStudylogItem.styles';

import { ReactComponent as ViewIcon } from '../../assets/images/view.svg';
import { ReactComponent as LikedIcon } from '../../assets/images/heart-filled.svg';
import { ReactComponent as UnLikeIcon } from '../../assets/images/heart.svg';

import type { Studylog } from '../../models/Studylogs';

const PopularStudylogItem = ({ item }: { item: Studylog }) => {
  const {
    title,
    mission,
    content,
    id,
    author,
    tags,
    createdAt,
    viewCount,
    liked,
    likesCount,
  } = item;

  return (
    <div css={[ContainerStyle]}>
      {/* 상단 영역 */}
      <Link to={`${PATH.STUDYLOGS}/${id}`}>
        <div css={[TopContainerStyle, getRandomBgColorStyle(id)]}>
          <span>
            [{mission.level?.name}]&nbsp;{mission.name}
          </span>
          <h2>{title}</h2>
          <span css={[DateAreaStyle]}>{new Date(createdAt).toLocaleDateString('ko-KR')}</span>
        </div>
      </Link>

      {/* 하단 영역 */}
      <div css={[BottomContainerStyle]}>
        {/* 프로필 영역 */}
        <Link to={`/${author.username}`} css={[ProfileAreaStyle]}>
          <div css={[FlexStyle, AlignItemsCenterStyle]}>
            <img src={author.imageUrl} alt="" />
            <span>{author.nickname}</span>
          </div>
        </Link>

        <div
          css={[
            FlexStyle,
            FlexColumnStyle,
            JustifyContentSpaceBtwStyle,
            css`
              height: 100%;
            `,
          ]}
        >
          {/* 컨텐츠 영역 */}
          <div css={[ContentsAreaStyle]}>
            <Link to={`${PATH.STUDYLOGS}/${id}`}>
              <div>{content.replace(/[#*>\n]/g, '')}</div>
            </Link>
          </div>

          <div>
            {/* 태그 영역 */}
            <ul css={[FlexStyle]}>
              {tags.slice(0, 2).map(({ name: tagName, id: tagId }) => (
                <Link to={`${PATH.STUDYLOGS}?tags=${tagId}`} key={tagId}>
                  <Chip title={tagName} onClick={() => {}}>
                    {tagName}
                  </Chip>
                </Link>
              ))}
            </ul>

            {/* 사용자 리액션 영역 */}
            <div css={[FlexStyle, getHorizontalGapStyle(0.6)]}>
              <div css={[UserReactionIconStyle]}>
                <ViewIcon width="2rem" height="2rem" />
                <span>{viewCount}</span>
              </div>
              <div css={[UserReactionIconStyle]}>
                {!liked ? (
                  <UnLikeIcon width="2rem" height="2rem" />
                ) : (
                  <LikedIcon width="2rem" height="2rem" />
                )}
                <span>{likesCount}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PopularStudylogItem;
