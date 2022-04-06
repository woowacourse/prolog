import { css } from '@emotion/react';

// 메인페이지 전체 적용
export const SectionHeaderGapStyle = css`
  padding-left: 1.2rem;
  margin-bottom: 1.2rem;

  @media screen and (max-width: 420px) {
    padding-left: 0.8rem;
    margin-bottom: 0.4rem;
  }
`;

// 인기있는 학습로그
export const PopularStudylogListStyle = css`
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
`;

export const PopularStudylogListRightControlStyle = css`
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
`;
