import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { COLOR } from '../../constants';

export const Container = styled.li`
  width: 100%;
  height: 100%;
  padding: 10px;
  border-radius: 8px;
  background-color: #ffffff;
  list-style: none;

  &:hover {
    background-color: #f2f2f2;
  }
`;

export const Anchor = styled.a``;

export const ThumbnailWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  aspect-ratio: 16/9;
  width: 100%;
  border-radius: 15px;
  margin-bottom: 10px;
`;

export const Thumbnail = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 8px;
  object-fit: cover;
`;

export const ArticleInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 100%;
  padding: 10px;
`;

export const ArticleInfoWrapper = styled.div`
  display: flex;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-break: break-all;
  font-size: 14px;
`;

export const UserName = styled.p`
  margin: 0;
  color: ${COLOR.DARK_GRAY_400};
`;

export const Title = styled.p`
  width: 100%;
  height: 50px;
  margin: 0;
  color: ${COLOR.BLACK_900};
  font-size: 16px;
  font-weight: 700;
  text-overflow: ellipsis;
  overflow: hidden;
  word-break: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
`;

export const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
`;

export const ArticleBookmarkButtonStyle = css`
  width: initial;
  background-color: transparent;
  text-align: right;

  & > img {
    width: 2.3rem;
    height: 2.3rem;
  }
`;

export const PublishedAt = styled.span`
  width: 100%;
  color: ${COLOR.DARK_GRAY_400};
  text-align: right;
  font-size: 12px;
  font-weight: 700;
`;
