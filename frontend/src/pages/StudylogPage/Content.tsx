/** @jsxImportSource @emotion/react */

import React, { MouseEventHandler, useContext } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import { Button, BUTTON_SIZE, Card, ProfileChip } from '../../components';
import ViewCount from '../../components/Count/ViewCount';
import {
  AlignItemsBaseLineStyle,
  FlexStyle,
  JustifyContentSpaceBtwStyle,
} from '../../styles/flex.styles';
import {
  BottomContainer,
  ButtonList,
  CardInner,
  DeleteButtonStyle,
  EditButtonStyle,
  IssuedDate,
  Mission,
  ProfileChipStyle,
  SubHeader,
  SubHeaderRightContent,
  Tags,
  Title,
  ViewerWrapper,
} from './styles';
import { QuestionAnswer, Studylog } from '../../models/Studylogs';
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
import { ALERT_MESSAGE, CONFIRM_MESSAGE, ERROR_MESSAGE, PATH } from '../../constants';
import { useHistory, useParams } from 'react-router-dom';
import useSnackBar from '../../hooks/useSnackBar';
import { useMutation } from 'react-query';
import { requestDeleteStudylog } from '../../service/requests';
import { SUCCESS_MESSAGE } from '../../constants/message';
import { useDeleteStudylogMutation } from '../../hooks/queries/studylog';
import { UserContext } from '../../contexts/UserProvider';

interface Props {
  studylog: Studylog;
  toggleLike: MouseEventHandler<HTMLButtonElement>;
  toggleScrap: MouseEventHandler<HTMLButtonElement>;
  // ProfileChip 내부 타이핑 불가로인하여 any로 단언
  goAuthorProfilePage: any;
  answers: QuestionAnswer[];
}

const Content: React.FC<Props> = ({
  studylog,
  toggleLike,
  toggleScrap,
  goAuthorProfilePage,
  answers,
}) => {
  const {
    author,
    mission,
    title,
    content,
    tags,
    createdAt,
    viewCount,
    liked,
    likesCount,
    scrap,
  } = studylog;

  const history = useHistory();
  const { user } = useContext(UserContext);
  const { accessToken, username } = user;
  const { id } = useParams<{ id: string }>();

  const { mutate: deleteStudylog } = useDeleteStudylogMutation();

  const goEditTargetPost = () => {
    history.push(`${PATH.STUDYLOG}/${id}/edit`);
  };
  return (
    <Card size="LARGE">
      <CardInner>
        <div>
          <SubHeader>
            <Mission>{mission?.name}</Mission>
            <SubHeaderRightContent></SubHeaderRightContent>
          </SubHeader>

          <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
            <Title>{title}</Title>
          </div>
          <div css={[FlexStyle, JustifyContentSpaceBtwStyle]}>
            <div>
              <div
                css={[
                  css`
                    display: flex;
                    align-items: center;
                    margin-bottom: 2rem;
                  `,
                ]}
              >
                <ProfileChip
                  imageSrc={author?.imageUrl || defaultProfileImage}
                  cssProps={ProfileChipStyle}
                  onClick={goAuthorProfilePage}
                >
                  {author?.nickname}
                </ProfileChip>
                <IssuedDate>{new Date(createdAt).toLocaleString('ko-KR')}</IssuedDate>
              </div>
            </div>

            <div>
              {username === author?.username && (
                <ButtonList>
                  {[
                    { title: '수정', cssProps: EditButtonStyle, onClick: goEditTargetPost },
                    {
                      title: '삭제',
                      cssProps: DeleteButtonStyle,
                      onClick: () => {
                        if (!window.confirm(CONFIRM_MESSAGE.DELETE_STUDYLOG)) return;
                        deleteStudylog({ id, accessToken });
                      },
                    },
                  ].map(({ title, cssProps, onClick }) => (
                    <Button
                      key={title}
                      size={BUTTON_SIZE.X_SMALL}
                      type="button"
                      cssProps={cssProps}
                      onClick={onClick}
                    >
                      {title}
                    </Button>
                  ))}
                </ButtonList>
              )}
            </div>
          </div>

          <div
            css={[
              css`
                display: flex;
                align-items: center;
                border-top: 1px solid #e6e6e6;
                border-bottom: 1px solid #e6e6e6;
                justify-content: flex-end;
                padding-right: 1rem;
              `,
            ]}
          >
            <ViewCount count={viewCount} />
            <Like liked={liked} likesCount={likesCount} onClick={toggleLike} />
            <Scrap scrap={scrap} onClick={toggleScrap} />
          </div>
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
          {/*<div*/}
          {/*  css={[*/}
          {/*    css`*/}
          {/*      display: flex;*/}
          {/*      align-items: center;*/}
          {/*      justify-content: flex-end;*/}
          {/*      padding-right: 1rem;*/}
          {/*    `,*/}
          {/*  ]}*/}
          {/*>*/}
          {/*  <ViewCount count={viewCount} />*/}
          {/*  <Like liked={liked} likesCount={likesCount} onClick={toggleLike} />*/}
          {/*  <Scrap scrap={scrap} onClick={toggleScrap} />*/}
          {/*</div>*/}
        </BottomContainer>
      </CardInner>
    </Card>
  );
};

export default Content;
