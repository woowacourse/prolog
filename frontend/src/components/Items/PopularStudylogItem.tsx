/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';

import { Chip } from '..';
import { PATH } from '../../enumerations/path';
import {
  AlignItemsCenterStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import { getColumnGapStyle } from '../../styles/layout.styles';
import {
  ContainerStyle,
  TopContainerStyle,
  ProfileAreaStyle,
  UserReactionIconStyle,
  getRandomBgColorStyle,
  BottomContainerStyle,
  ContentsAreaStyle,
  TagContainerStyle,
  TitleLink,
} from './PopularStudylogItem.styles';

import { ReactComponent as ViewIcon } from '../../assets/images/view.svg';
import { ReactComponent as LikedIcon } from '../../assets/images/heart-filled.svg';
import { ReactComponent as UnLikeIcon } from '../../assets/images/heart.svg';
import { ReactComponent as ScrapIcon } from '../../assets/images/scrap_filled.svg';
import { ReactComponent as UnScrapIcon } from '../../assets/images/scrap.svg';

import type { Studylog } from '../../models/Studylogs';

const PopularStudylogItem = ({ item }: { item: Studylog }) => {
  const { title, content, id, author, tags, createdAt, viewCount, liked, likesCount, scrap } = item;

  return (
    <div css={[ContainerStyle]}>
      {/* 상단 영역 */}
      <div css={[TopContainerStyle, getRandomBgColorStyle(id)]}>
        {/* 프로필 영역 */}
        <Link to={`/${author.username}`} css={[ProfileAreaStyle, FlexStyle, AlignItemsCenterStyle]}>
          <img src={author.imageUrl} alt="" />
          <span>{author.nickname}</span>
        </Link>
        {/* 제목 영역 */}
        <Link to={`${PATH.STUDYLOGS}/${id}`} css={[TitleLink]}>
          <h2>{title}</h2>
        </Link>
      </div>

      {/* 하단 영역 */}
      <div css={[BottomContainerStyle]}>
        {/* 컨텐츠 영역 */}
        <div css={[ContentsAreaStyle]}>
          <Link to={`${PATH.STUDYLOGS}/${id}`}>
            <div>{content.replace(/[#*>\n]/g, '')}</div>
          </Link>
        </div>

        {/* 태그 영역 */}
        <ul css={[TagContainerStyle]} style={{margin: "0.5rem 0"}}>
          {tags.slice(0, 2).map(({ name: tagName, id: tagId }) => (
            <Link to={`${PATH.STUDYLOGS}?tags=${tagId}`} key={tagId}>
              <Chip title={tagName} onClick={() => {}}>
                {tagName}
              </Chip>
            </Link>
          ))}
        </ul>

        {/* 사용자 리액션 영역 */}
        <div
          css={[
            FlexStyle,
            JustifyContentSpaceBtwStyle,
            AlignItemsCenterStyle,
            getColumnGapStyle(0.6),
          ]}
        >
          <div css={[FlexStyle, AlignItemsCenterStyle]}>
            <div css={[FlexStyle, AlignItemsCenterStyle, UserReactionIconStyle]}>
              <ViewIcon width="2rem" height="2rem" />
              <span>{viewCount}</span>
            </div>
            <div css={[FlexStyle, AlignItemsCenterStyle, UserReactionIconStyle]}>
              {!liked ? (
                <UnLikeIcon width="2rem" height="2rem" />
              ) : (
                <LikedIcon width="2rem" height="2rem" />
              )}
              <span>{likesCount}</span>
            </div>
            <div css={[FlexStyle, AlignItemsCenterStyle, UserReactionIconStyle]}>
              {!scrap ? (
                <UnScrapIcon width="1.6rem" height="1.6rem" />
              ) : (
                <ScrapIcon width="1.6rem" height="1.6rem" />
              )}
            </div>
          </div>
          <span>{new Date(createdAt).toLocaleDateString('ko-KR')}</span>
        </div>
      </div>
    </div>
  );
};

export default PopularStudylogItem;
