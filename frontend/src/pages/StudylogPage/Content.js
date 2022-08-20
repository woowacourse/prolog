/** @jsxImportSource @emotion/react */

import { Card, ProfileChip } from '../../components';
import ViewCount from '../../components/ViewCount/ViewCount';
import {
  AlignItemsBaseLineStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import {
  BottomContainer,
  CardInner,
  IssuedDate,
  Mission,
  ProfileChipStyle,
  SubHeader,
  SubHeaderRightContent,
  Tags,
  Title,
  ViewerWrapper,
} from './styles';

import defaultProfileImage from '../../assets/images/no-profile-image.png';
import { css } from '@emotion/react';
import Like from '../../components/Reaction/Like';
import Scrap from '../../components/Reaction/Scrap';

// 마크다운
import { Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'prismjs/themes/prism.css';
import Prism from 'prismjs';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js';

const Content = ({ studylog, toggleLike, toggleScrap, goAuthorProfilePage }) => {
  const {
    author = null,
    mission = {},
    title = '',
    content = '',
    tags = [],
    createdAt = null,
    viewCount = 0,
    liked = false,
    likesCount = 0,
    scrap = false,
  } = studylog;

  return (
    <Card size="LARGE">
      <CardInner>
        <div>
          <SubHeader>
            <Mission>{mission?.name}</Mission>
            <SubHeaderRightContent>
              <IssuedDate>{new Date(createdAt).toLocaleString('ko-KR')}</IssuedDate>
            </SubHeaderRightContent>
          </SubHeader>

          <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
            <Title>{title}</Title>
            <ViewCount count={viewCount} />
          </div>

          <ProfileChip
            imageSrc={author?.imageUrl || defaultProfileImage}
            cssProps={ProfileChipStyle}
            onClick={goAuthorProfilePage}
          >
            {author?.nickname}
          </ProfileChip>
        </div>

        <ViewerWrapper>
          {content && (
            <Viewer
              initialValue={content}
              extendedAutolinks={true}
              plugins={[[codeSyntaxHighlight, { highlighter: Prism }]]}
            />
          )}
        </ViewerWrapper>

        <BottomContainer>
          <Tags>
            {tags?.map(({ id, name }) => (
              <span key={id}>{`#${name} `}</span>
            ))}
          </Tags>
          <div
            css={[
              FlexStyle,
              AlignItemsBaseLineStyle,
              css`
                > *:not(:last-child) {
                  margin-right: 1rem;
                }
              `,
            ]}
          >
            <Like liked={liked} likesCount={likesCount} onClick={toggleLike} />
            <Scrap scrap={scrap} onClick={toggleScrap} />
          </div>
        </BottomContainer>
      </CardInner>
    </Card>
  );
};

export default Content;
