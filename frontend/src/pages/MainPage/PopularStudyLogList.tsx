/** @jsxImportSource @emotion/react */

import { Link } from 'react-router-dom';
import { css } from '@emotion/react';

import { ReactComponent as ViewIcon } from '../../assets/images/view.svg';
import { ReactComponent as LikedIcon } from '../../assets/images/heart-filled.svg';
import { ReactComponent as UnLikeIcon } from '../../assets/images/heart.svg';

import { COLOR, PATH } from '../../constants';
import { Chip } from '../../components';
import { Studylog } from '../../models/Studylogs';

// TODO: 팔레트 색상 추가
const RANDOM_COLOR_PALLETS = [
  '#ff9797',
  '#ff9ebb',
  '#ffcb20',
  '#5ce17d',
  '#a5e1e6',
  '#74bcff',
  '#c886ce',
];

const getRandomColor = (id: number): string => {
  return RANDOM_COLOR_PALLETS[id % RANDOM_COLOR_PALLETS.length];
};

const PopularStudyLogList = ({ studylogs }: { studylogs: Studylog[] }): JSX.Element => {
  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <h2
        css={css`
          margin-bottom: 1.2rem;
          padding-left: 1.2rem;
        `}
      >
        😎 인기있는 학습로그
      </h2>
      <ul
        css={css`
          width: 100%;
          height: 34rem;

          display: grid;
          justify-content: content;
          align-items: center;
          grid-template-columns: repeat(10, 1fr);

          position: relative;

          > *:not(:last-child) {
            margin-right: 1.6rem;
          }

          > *:last-child {
            margin-right: 0.2rem;
          }

          overflow-x: scroll;
        `}
      >
        {studylogs?.map(
          ({
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
          }) => (
            <li key={id}>
              <div
                css={css`
                  width: 24rem;
                  height: 32rem;

                  border-radius: 1.6rem;

                  box-shadow: 1px 1px 2px 0 rgba(0, 0, 0, 0.4);
                `}
              >
                <Link to={`${PATH.POST}/${id}`}>
                  <div
                    css={css`
                      width: 100%;
                      height: 17rem;

                      padding: 1rem 1.6rem 4rem;

                      background-color: ${getRandomColor(id)};
                      border-radius: 1.6rem;
                      border-bottom-right-radius: 0;
                      border-bottom-left-radius: 0;

                      position: relative;

                      overflow: hidden;

                      :hover {
                        h2 {
                          text-decoration: underline;
                        }
                      }

                      h2 {
                        font-size: 2rem;
                        line-height: 1.4;
                        font-weight: bold;
                        color: black;

                        line-break: anywhere;

                        display: -webkit-box;
                        -webkit-line-clamp: 3;
                        -webkit-box-orient: vertical;
                        overflow: hidden;
                        text-overflow: ellipsis;
                      }

                      span {
                        font-size: 1.2rem;
                      }
                    `}
                  >
                    <span>
                      [{mission.level?.name}]&nbsp;{mission.name}
                    </span>
                    <h2>{title}</h2>
                    <span
                      css={css`
                        position: absolute;
                        bottom: 1rem;
                        right: 1rem;
                      `}
                    >
                      {new Date(createdAt).toLocaleDateString('ko-KR')}
                    </span>
                  </div>
                </Link>
                <div
                  css={css`
                    width: 100%;
                    height: 15rem;

                    padding: 1.8rem 1.6rem 1rem;

                    background-color: white;
                    border-radius: inherit;
                    border-top-right-radius: 0;
                    border-top-left-radius: 0;

                    position: relative;

                    span {
                      font-size: 1.2rem;
                    }
                  `}
                >
                  <Link
                    to={`/${author.nickname}`}
                    css={css`
                      :hover {
                        span {
                          text-decoration: underline;
                        }

                        img {
                          opacity: 0.8;
                        }
                      }
                    `}
                  >
                    <div
                      css={css`
                        position: absolute;
                        left: 1rem;
                        top: 0;

                        transform: translateY(-80%);

                        display: flex;
                        align-items: center;

                        span {
                          padding-bottom: 1rem;
                          margin-left: 1rem;

                          font-size: 1.6rem;
                        }
                      `}
                    >
                      <img
                        src={author.imageUrl}
                        alt=""
                        css={css`
                          width: 4.8rem;
                          height: 4.8rem;

                          border-radius: 1.6rem;

                          z-index: 10;
                        `}
                      />
                      <span>{author.nickname}</span>
                    </div>
                  </Link>
                  <div
                    css={css`
                      height: 100%;

                      display: flex;
                      flex-direction: column;
                      justify-content: space-between;
                    `}
                  >
                    <Link
                      to={`${PATH.POST}/${id}`}
                      css={css`
                        :hover {
                          div {
                            text-decoration: underline;
                          }
                        }
                      `}
                    >
                      <div
                        css={css`
                          font-size: 1.4rem;

                          display: -webkit-box;
                          -webkit-line-clamp: 3;
                          -webkit-box-orient: vertical;
                          overflow: hidden;
                          text-overflow: ellipsis;
                        `}
                      >
                        {/* TODO: 마크다운 제거를 위한 정규식 문자열 추가*/}
                        {content.replace(/[#*>\n]/g, '')}
                      </div>
                    </Link>

                    <div>
                      <ul
                        css={css`
                          display: flex;
                        `}
                      >
                        {tags.slice(0, 2).map(({ name: tagName, id: tagId }) => (
                          <Link to={`${PATH.STUDYLOG}?tags=${tagId}`} key={tagId}>
                            <Chip title={tagName} onClick={() => {}}>
                              {tagName}
                            </Chip>
                          </Link>
                        ))}
                      </ul>
                      <div
                        css={css`
                          display: flex;

                          > *:not(:last-child) {
                            margin-right: 0.6rem;
                          }
                        `}
                      >
                        <div
                          css={css`
                            flex-shrink: 0;
                            color: ${COLOR.LIGHT_GRAY_900};
                            font-size: 1.4rem;
                            margin-top: 0.5rem;

                            & > svg {
                              margin-right: 0.25rem;
                            }

                            span {
                              vertical-align: top;
                            }
                          `}
                        >
                          <ViewIcon width="2rem" height="2rem" />
                          <span>{viewCount}</span>
                        </div>
                        <div
                          css={css`
                            flex-shrink: 0;
                            color: ${COLOR.LIGHT_GRAY_900};
                            font-size: 1.4rem;
                            margin-top: 0.5rem;

                            & > svg {
                              margin-right: 0.25rem;
                            }

                            span {
                              vertical-align: top;
                            }
                          `}
                        >
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
            </li>
          )
        )}
      </ul>
      <div
        css={css`
          width: 3.6rem;
          height: 300px;

          background-color: rgba(0, 0, 0, 0);

          position: absolute;
          bottom: 0;
          right: 0;

          display: flex;
          align-items: center;

          button {
            width: 3.2rem;
            height: 3.2rem;

            margin-right: auto;

            background-color: rgba(0, 0, 0, 0.7);
            border-radius: 50%;

            color: white;

            opacity: 0;
          }

          :hover {
            button {
              opacity: 1;
            }
          }
        `}
      >
        <button>다음</button>
      </div>
    </section>
  );
};

export default PopularStudyLogList;
